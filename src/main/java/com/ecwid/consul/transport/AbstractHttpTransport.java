package com.ecwid.consul.transport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecwid.consul.ConsulException;

/**
 * The Abstract HTTP transport code that interfaces with Apache HTTP Client 5.x.
 * Provides both synchronous and asynchronous clients.
 * 
 * The following modifications were made from the original APL 2.0 code:
 * <ul>
 * <li>Migration from Apache HTTPClient 4.x to 5.x</li>
 * <li>Added code for {@link AsyncHttpClient}</li>
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
		HttpUtils.addHeadersToRequest(httpGet, request.getToken(), request.getHeaders());
		return executeRequest(httpGet);
	}

	@SuppressWarnings("resource") // closing ByteArrayEntity is a no-op
	@Override
	public final HttpResponse makePutRequest(HttpRequest request) {
		HttpPut httpPut = new HttpPut(request.getURI());
		HttpUtils.addHeadersToRequest(httpPut, request.getToken(), request.getHeaders());
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
		HttpUtils.addHeadersToRequest(httpDelete, request.getToken(), request.getHeaders());
		return executeRequest(httpDelete);
	}

//	@Override
//	public final CompletableFuture<HttpResponse> makeAsyncGetRequest(HttpRequest request) {
//		SimpleHttpRequest httpGet = SimpleRequestBuilder.get(request.getURI()).build();
//		addHeadersToRequest(httpGet, request.getToken(), request.getHeaders());
//		return executeAsyncRequest(httpGet);
//	}

//	@Override
//	public final CompletableFuture<HttpResponse> makeAsyncPutRequest(HttpRequest request) {
//		SimpleHttpRequest httpPut = SimpleRequestBuilder.put(request.getURI()).build();
//		addHeadersToRequest(httpPut, request.getToken(), request.getHeaders());
//		if (request.getContent() != null) {
//			String cType = request.getContentType();
//			// Assume content type is JSON unless it is explicitly set
//			ContentType contentType = cType != null ? ContentType.parse(cType) : ContentType.APPLICATION_JSON;
//			httpPut.setBody(request.getContent(), contentType);
//		}
//		return executeAsyncRequest(httpPut);
//	}

//	@Override
//	public final CompletableFuture<HttpResponse> makeAsyncDeleteRequest(HttpRequest request) {
//		SimpleHttpRequest httpDelete = SimpleRequestBuilder.delete(request.getURI()).build();
//		addHeadersToRequest(httpDelete, request.getToken(), request.getHeaders());
//		return executeAsyncRequest(httpDelete);
//	}

	private HttpResponse executeRequest(@NonNull HttpUriRequest httpRequest) {
		logRequest(httpRequest);
		try {
			return getHttpClient().execute(httpRequest, HttpConsulResponseHandler.INSTANCE);
		} catch (IOException e) {
			throw new TransportException(e);
		} catch (RuntimeException e) {
			httpRequest.abort();
			throw e;
		}
	}

//	private CompletableFuture<HttpResponse> executeAsyncRequest(@NonNull SimpleHttpRequest httpRequest) {
//		logRequest(httpRequest);
//		SimpleHttpResponseCompletableFutureCallback callback = new SimpleHttpResponseCompletableFutureCallback();
//		getAsyncHttpClient().execute(SimpleRequestProducer.create(httpRequest), SimpleResponseConsumer.create(), null,
//				null, callback);
//		return callback.getCompletableFuture();
//	}

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