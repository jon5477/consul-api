package com.ecwid.consul.transport;

import java.util.concurrent.CompletableFuture;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface HttpTransport {
	HttpResponse makeGetRequest(HttpRequest request);

	HttpResponse makePutRequest(HttpRequest request);

	HttpResponse makeDeleteRequest(HttpRequest request);

	CompletableFuture<HttpResponse> makeAsyncGetRequest(HttpRequest request);

	CompletableFuture<HttpResponse> makeAsyncPutRequest(HttpRequest request);

	CompletableFuture<HttpResponse> makeAsyncDeleteRequest(HttpRequest request);
}