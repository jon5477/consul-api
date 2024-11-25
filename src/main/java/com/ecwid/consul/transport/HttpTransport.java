package com.ecwid.consul.transport;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 */
public interface HttpTransport {
	HttpClientConnectionManager getConnectionManager();

	HttpClient getHttpClient();

	HttpResponse makeGetRequest(HttpRequest request);

	HttpResponse makePutRequest(HttpRequest request);

	HttpResponse makeDeleteRequest(HttpRequest request);

//	CompletableFuture<HttpResponse> makeAsyncGetRequest(HttpRequest request);

//	CompletableFuture<HttpResponse> makeAsyncPutRequest(HttpRequest request);

//	CompletableFuture<HttpResponse> makeAsyncDeleteRequest(HttpRequest request);
}