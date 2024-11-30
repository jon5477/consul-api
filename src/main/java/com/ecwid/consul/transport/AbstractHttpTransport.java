package com.ecwid.consul.transport;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecwid.consul.TimedOutException;

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

	@Override
	public final ConsulHttpResponse makeGetRequest(ConsulHttpRequest request) {
		// Timeout is dependent on a wait query parameter
		HttpRequest.Builder req = HttpRequest.newBuilder().uri(request.getURI())
				.timeout(ClientUtils.DEFAULT_REQUEST_TIMEOUT).GET();
		HttpUtils.addHeadersToRequest(req, request.getToken(), request.getHeaders());
		return executeRequest(req.build());
	}

	@SuppressWarnings("resource") // closing ByteArrayEntity is a no-op
	@Override
	public final ConsulHttpResponse makePutRequest(ConsulHttpRequest request) {
		HttpRequest.Builder req = HttpRequest.newBuilder().uri(request.getURI())
				.timeout(ClientUtils.DEFAULT_REQUEST_TIMEOUT);
		HttpUtils.addHeadersToRequest(req, request.getToken(), request.getHeaders());
		if (request.getContent() != null) {
			String contentType = Optional.ofNullable(request.getContentType()).orElse("application/json");
			req.header("Content-Type", contentType).PUT(BodyPublishers.ofByteArray(request.getContent()));
		}
		return executeRequest(req.build());
	}

	@Override
	public final ConsulHttpResponse makeDeleteRequest(ConsulHttpRequest request) {
		HttpRequest.Builder req = HttpRequest.newBuilder().uri(request.getURI())
				.timeout(ClientUtils.DEFAULT_REQUEST_TIMEOUT).DELETE();
		HttpUtils.addHeadersToRequest(req, request.getToken(), request.getHeaders());
		return executeRequest(req.build());
	}

	private ConsulHttpResponse executeRequest(@NonNull HttpRequest req) {
		logRequest(req);
		// Determine if this HTTP request will block
		String query = req.uri().getQuery();
		boolean blockingQuery = query != null && query.contains("index");
		try {
			HttpResponse<InputStream> response = getHttpClient().send(req, BodyHandlers.ofInputStream());
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
						sb.append(';');
					}
				}
				sb.append("] ");
			}
			LOGGER.trace(sb.toString());
		}
	}
}