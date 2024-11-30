package com.ecwid.consul.transport;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.TimedOutException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Request;

/**
 * The HTTP transport code that interfaces with the underlying Java
 * {@link HttpClient}.
 * 
 * The following modifications were made from the original APL 2.0 code:
 * <ul>
 * <li>Migration from Apache HTTPClient 4.x to Java {@link HttpClient}</li>
 * <li>Added code for building {@link HttpRequest}s.</li>
 * </ul>
 * 
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 * 
 */
abstract class AbstractHttpTransport implements HttpTransport {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpTransport.class);

	/**
	 * Makes a HTTP request to the given Consul Agent {@link URI} with the specified
	 * {@link HttpMethod} and {@link Request}.
	 * 
	 * @param agentUri The Consul Agent {@link URI}.
	 * @param method   The HTTP method to use.
	 * @param request  The {@link Request} to make.
	 * @return The {@link ConsulHttpResponse} from the given API.
	 * @throws TimedOutException If the request timed-out, this can happen due to a
	 *                           {@link Thread#interrupt()} or an
	 *                           {@link InterruptedException}.
	 */
	@Override
	public final ConsulHttpResponse makeRequest(@NonNull URI agentUri, @NonNull HttpMethod method,
			@NonNull Request request) throws TimedOutException {
		Objects.requireNonNull(agentUri, "agent URI cannot be null");
		Objects.requireNonNull(method, "HTTP method cannot be null");
		Objects.requireNonNull(request, "request cannot be null");
		Map<String, String> allQueryParams = getAllQueryParameters(request);
		// Grab the full URI that the HTTP client will make a request to
		URI reqUri = buildURI(agentUri, request.getEndpoint(), allQueryParams);
		HttpRequest.Builder reqBuilder = HttpRequest.newBuilder(reqUri);
		// Set the timeout on this request - dependent on the "wait" query parameter
		if (allQueryParams.containsKey("wait")) {
			// TODO This should be adjusted to the duration on the wait parameter + 1 second
			reqBuilder.timeout(QueryParams.MAX_WAIT_TIME);
		} else {
			reqBuilder.timeout(ClientUtils.DEFAULT_REQUEST_TIMEOUT);
		}
		// Add HTTP headers to the request
		HttpUtils.addHeadersToRequest(reqBuilder, request.getToken(), request.getHeaders());
		// Specify the HTTP method and body
		switch (method) {
			case GET:
				reqBuilder.GET();
				break;
			case DELETE:
				reqBuilder.DELETE();
				break;
			case PUT:
			default: // Gracefully handle "default" case :)
				if (request.getContent() != null) {
					String contentType = Optional.ofNullable(request.getContentType()).orElse("application/json");
					reqBuilder.header("Content-Type", contentType).method(method.name(),
							BodyPublishers.ofByteArray(request.getContent()));
				} else {
					reqBuilder.method(method.name(), BodyPublishers.noBody());
				}
				break;
		}
		return executeRequest(reqBuilder.build());
	}

	/**
	 * Constructs the given HTTP request from the {@link Request} instance and sends
	 * it to the Consul API.
	 * 
	 * @param request The {@link HttpRequest} instance to use for making the HTTP
	 *                request.
	 * @return A {@link ConsulHttpResponse} from the Consul API.
	 * @throws TimedOutException  If the request timed-out, this can happen due to a
	 *                            {@link Thread#interrupt()} or an
	 *                            {@link InterruptedException}.
	 * @throws TransportException If an {@link IOException} occurs while performing
	 *                            the request.
	 */
	private ConsulHttpResponse executeRequest(@NonNull HttpRequest request) {
		logRequest(request);
		try {
			HttpResponse<InputStream> response = getHttpClient().send(request, BodyHandlers.ofInputStream());
			return HttpUtils.parseResponse(response);
		} catch (InterruptedException e) {
			// propagate the interrupt signal
			Thread.currentThread().interrupt();
			// throw this custom exception so clients can handle it
			throw new TimedOutException(e);
		} catch (IOException e) {
			throw new TransportException(e);
		}
	}

	@NonNull
	private Map<String, String> getAllQueryParameters(@NonNull Request request) {
		Map<String, String> queryParams = new HashMap<>();
		// read the normal query parameters
		for (QueryParameters params : request.getQueryParameters()) {
			queryParams.putAll(params.getQueryParameters());
		}
		// read the extra query parameters (these take precedence)
		queryParams.putAll(request.getExtraQueryParameters());
		return queryParams;
	}

	/**
	 * Creates a full {@link URI} to the Consul API endpoint.
	 * 
	 * @param agentUri    The {@link URI} to the Consul agent.
	 * @param endpoint    The Consul API endpoint to use.
	 * @param queryParams The {@link Map} of key-value query parameters to include.
	 * @return The {@link URI} to the Consul API endpoint including query
	 *         parameters.
	 */
	private URI buildURI(@NonNull URI agentUri, @NonNull String endpoint, @NonNull Map<String, String> queryParams) {
		Objects.requireNonNull(agentUri, "agent URI cannot be null");
		Objects.requireNonNull(endpoint, "API endpoint cannot be null");
		try {
			URLEncoder.encode(endpoint, StandardCharsets.UTF_8);
			String queryStr = queryParams.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
					.collect(Collectors.joining("&"));
			String query = !queryStr.isEmpty() ? queryStr : null;
			return new URI(agentUri.getScheme(), agentUri.getUserInfo(), agentUri.getHost(), agentUri.getPort(),
					agentUri.getPath() + endpoint, query, null);
		} catch (URISyntaxException e) {
			throw new ConsulException(e);
		}
	}

	private void logRequest(@NonNull HttpRequest request) {
		if (LOGGER.isTraceEnabled()) {
			StringBuilder sb = new StringBuilder();
			// method
			sb.append(request.method()).append(' ').append(request.uri()).append(' ');
			// headers, if any
			HttpHeaders headers = request.headers();
			if (!headers.map().isEmpty()) {
				sb.append("Headers:[");
				for (Iterator<Entry<String, List<String>>> i = headers.map().entrySet().iterator(); i.hasNext();) {
					Entry<String, List<String>> header = i.next();
					sb.append(header.getKey()).append('=').append(header.getValue().get(0));
					if (i.hasNext()) {
						sb.append("; ");
					}
				}
				sb.append("] ");
			}
			LOGGER.trace(sb.toString());
		}
	}
}