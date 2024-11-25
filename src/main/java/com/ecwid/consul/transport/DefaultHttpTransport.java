package com.ecwid.consul.transport;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Default HTTP client This class is thread safe
 *
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class DefaultHttpTransport extends AbstractHttpTransport {
	private final HttpClientConnectionManager connectionManager;
	private final HttpClient httpClient;
//	private final AsyncClientConnectionManager asyncConnectionManager;
//	private final HttpAsyncClient asyncHttpClient;

	public DefaultHttpTransport(@NonNull HttpClientConnectionManager connectionManager,
			@NonNull HttpClient httpClient) {
		this.connectionManager = connectionManager;
		this.httpClient = httpClient;
	}

	@Override
	public final HttpClientConnectionManager getConnectionManager() {
		return connectionManager;
	}

	@Override
	public final HttpClient getHttpClient() {
		return httpClient;
	}
}