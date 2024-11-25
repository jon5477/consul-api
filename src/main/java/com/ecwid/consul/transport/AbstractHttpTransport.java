package com.ecwid.consul.transport;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.HeaderGroup;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.json.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The Abstract HTTP transport code that interfaces with Apache HTTP Client 5.x.
 * Provides both synchronous and asynchronous clients.
 * 
 * The following modifications were made from the original APL 2.0 code:
 * <ul>
 * <li>Migration from Apache HTTPClient 4.x to 5.x</li>
 * <li>Added support for {@link AsyncHttpClient}</li>
 * </ul>
 * 
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 * 
 */
abstract class AbstractHttpTransport implements HttpTransport {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpTransport.class);

	@Override
	public final HttpResponse makeGetRequest(HttpRequest request) {
		HttpGet httpGet = new HttpGet(request.getURI());
		addHeadersToRequest(httpGet, request.getToken(), request.getHeaders());
		return executeRequest(httpGet);
	}

	@SuppressWarnings("resource") // closing ByteArrayEntity is a no-op
	@Override
	public final HttpResponse makePutRequest(HttpRequest request) {
		HttpPut httpPut = new HttpPut(request.getURI());
		addHeadersToRequest(httpPut, request.getToken(), request.getHeaders());
		if (request.getContent() != null) {
			String cType = request.getContentType();
			// Assume content type is JSON unless it is explicitly set
			ContentType contentType = cType != null ? ContentType.parse(cType) : ContentType.APPLICATION_JSON;
			httpPut.setEntity(new ByteArrayEntity(request.getContent(), contentType));
		}
		return executeRequest(httpPut);
	}

	@Override
	public final HttpResponse makeDeleteRequest(HttpRequest request) {
		HttpDelete httpDelete = new HttpDelete(request.getURI());
		addHeadersToRequest(httpDelete, request.getToken(), request.getHeaders());
		return executeRequest(httpDelete);
	}

	@Override
	public final CompletableFuture<HttpResponse> makeAsyncGetRequest(HttpRequest request) {
		SimpleHttpRequest httpGet = SimpleRequestBuilder.get(request.getURI()).build();
		addHeadersToRequest(httpGet, request.getToken(), request.getHeaders());
		return executeAsyncRequest(httpGet);
	}

	@Override
	public final CompletableFuture<HttpResponse> makeAsyncPutRequest(HttpRequest request) {
		SimpleHttpRequest httpPut = SimpleRequestBuilder.put(request.getURI()).build();
		addHeadersToRequest(httpPut, request.getToken(), request.getHeaders());
		if (request.getContent() != null) {
			String cType = request.getContentType();
			// Assume content type is JSON unless it is explicitly set
			ContentType contentType = cType != null ? ContentType.parse(cType) : ContentType.APPLICATION_JSON;
			httpPut.setBody(request.getContent(), contentType);
		}
		return executeAsyncRequest(httpPut);
	}

	@Override
	public final CompletableFuture<HttpResponse> makeAsyncDeleteRequest(HttpRequest request) {
		SimpleHttpRequest httpDelete = SimpleRequestBuilder.delete(request.getURI()).build();
		addHeadersToRequest(httpDelete, request.getToken(), request.getHeaders());
		return executeAsyncRequest(httpDelete);
	}

	/**
	 * Provides a synchronous HTTP client instance. Subclasses should override this
	 * method and provide a ready-to-use {@link HttpClient} instance.
	 *
	 * @return A ready-to-use {@link HttpClient} instance.
	 */
	protected abstract HttpClient getHttpClient();

	/**
	 * Provides an asynchronous HTTP client instance. Subclasses should override
	 * this method and provide a ready-to-use {@link HttpAsyncClient} instance.
	 * 
	 * @return A ready-to-use {@link HttpAsyncClient} instance.
	 */
	protected abstract HttpAsyncClient getAsyncHttpClient();

	private HttpResponse executeRequest(@NonNull HttpUriRequest httpRequest) {
		logRequest(httpRequest);
		try {
			return getHttpClient().execute(httpRequest, response -> {
				int statusCode = response.getCode();
				String statusMessage = response.getReasonPhrase();
				JsonNode content = null;
				try (HttpEntity entity = response.getEntity()) {
					if (entity != null && ContentType.APPLICATION_JSON.getMimeType().equals(entity.getContentType())) {
						try (InputStream is = entity.getContent()) {
							if (is != null) {
								content = JsonFactory.toJsonNode(is);
							}
						}
					}
				}
				Long consulIndex = parseUnsignedLong(response.getFirstHeader("X-Consul-Index"));
				Boolean consulKnownLeader = parseBoolean(response.getFirstHeader("X-Consul-Knownleader"));
				Long consulLastContact = parseUnsignedLong(response.getFirstHeader("X-Consul-Lastcontact"));
				return new HttpResponse(statusCode, statusMessage, content, consulIndex, consulKnownLeader,
						consulLastContact);
			});
		} catch (IOException e) {
			throw new TransportException(e);
		}
	}

	private CompletableFuture<HttpResponse> executeAsyncRequest(@NonNull SimpleHttpRequest httpRequest) {
		logRequest(httpRequest);
		SimpleHttpResponseCompletableFutureCallback callback = new SimpleHttpResponseCompletableFutureCallback();
		getAsyncHttpClient().execute(SimpleRequestProducer.create(httpRequest), SimpleResponseConsumer.create(), null,
				null, callback);
		return callback.getCompletableFuture();
	}

	static Long parseUnsignedLong(Header header) {
		if (header != null) {
			String value = header.getValue();
			if (value != null) {
				try {
					return Long.parseUnsignedLong(value);
				} catch (NumberFormatException e) {
					LOGGER.error("Failed to parse header {} as unsigned long", header.getName());
				}
			}
		}
		return null;
	}

	static Boolean parseBoolean(Header header) {
		if (header != null) {
			if ("true".equals(header.getValue())) {
				return true;
			}
			if ("false".equals(header.getValue())) {
				return false;
			}
			LOGGER.error("Failed to parse header {} as boolean", header.getName());
		}
		return null;
	}

	private void addHeadersToRequest(HeaderGroup request, char[] token, Map<String, String> headers) {
		if (token != null) {
			request.addHeader(new BasicHeader("X-Consul-Token", new String(token), true));
		}
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> headerValue : headers.entrySet()) {
				String name = headerValue.getKey();
				String value = headerValue.getValue();
				request.addHeader(new BasicHeader(name, value));
			}
		}
	}

	private void logRequest(org.apache.hc.core5.http.HttpRequest httpRequest) {
		if (LOGGER.isTraceEnabled()) {
			StringBuilder sb = new StringBuilder();
			// method
			sb.append(httpRequest.getMethod());
			sb.append(' ');
			// url
			try {
				sb.append(httpRequest.getUri());
			} catch (URISyntaxException e) {
				throw new ConsulException(e);
			}
			sb.append(' ');
			// headers, if any
			Iterator<Header> iterator = httpRequest.headerIterator();
			if (iterator.hasNext()) {
				sb.append("Headers:[");
				Header header = iterator.next();
				sb.append(header.getName()).append('=').append(header.getValue());
				while (iterator.hasNext()) {
					header = iterator.next();
					sb.append(header.getName()).append('=').append(header.getValue());
					sb.append(';');
				}
				sb.append("] ");
			}
			LOGGER.trace(sb.toString());
		}
	}
}