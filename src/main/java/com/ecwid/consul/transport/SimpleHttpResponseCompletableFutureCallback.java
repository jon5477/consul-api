package com.ecwid.consul.transport;

import java.util.concurrent.CompletableFuture;

import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A {@link FutureCallback} implementation that converts a
 * {@link SimpleHttpResponse} into a {@link HttpResponse} when the HTTP response
 * is received. This also communicates failure or cancellation to the Apache
 * HTTP client.
 * 
 * @author Jon Huang (jon5477)
 *
 */
final class SimpleHttpResponseCompletableFutureCallback implements FutureCallback<SimpleHttpResponse> {
	private final CompletableFuture<HttpResponse> cf = new CompletableFuture<>();

	SimpleHttpResponseCompletableFutureCallback() {
	}

	final CompletableFuture<HttpResponse> getCompletableFuture() {
		return cf;
	}

	@Override
	public final void completed(@NonNull SimpleHttpResponse result) {
		try {
			cf.complete(HttpUtils.parseResponse(result));
		} catch (Exception e) {
			cf.completeExceptionally(e);
		}
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