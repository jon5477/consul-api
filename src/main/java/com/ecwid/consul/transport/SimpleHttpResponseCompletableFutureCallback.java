package com.ecwid.consul.transport;

import java.util.concurrent.CompletableFuture;

import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;

/**
 * A {@link FutureCallback} implementation that converts a
 * {@link SimpleHttpResponse} into a {@link HttpResponse} when the HTTP response
 * is received. This also communicates failure or cancellation to the Apache
 * HTTP client.
 * 
 * @author Jon Huang
 *
 */
final class SimpleHttpResponseCompletableFutureCallback implements FutureCallback<SimpleHttpResponse> {
	private final CompletableFuture<HttpResponse> cf = new CompletableFuture<>();

	public final CompletableFuture<HttpResponse> getCompletableFuture() {
		return cf;
	}

	@Override
	public final void completed(SimpleHttpResponse result) {
		int statusCode = result.getCode();
		String statusMessage = result.getReasonPhrase();
		String content = result.getBody().getBodyText();
		Long consulIndex = AbstractHttpTransport.parseUnsignedLong(result.getFirstHeader("X-Consul-Index"));
		Boolean consulKnownLeader = AbstractHttpTransport.parseBoolean(result.getFirstHeader("X-Consul-Knownleader"));
		Long consulLastContact = AbstractHttpTransport.parseUnsignedLong(result.getFirstHeader("X-Consul-Lastcontact"));
		cf.complete(new HttpResponse(statusCode, statusMessage, content, consulIndex, consulKnownLeader,
				consulLastContact));
	}

	@Override
	public final void failed(Exception ex) {
		cf.completeExceptionally(ex);
	}

	@Override
	public final void cancelled() {
		cf.cancel(false);
	}
}