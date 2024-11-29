package com.ecwid.consul.transport;

import java.net.http.HttpClient;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Default HTTP client This class is thread safe
 *
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class DefaultHttpTransport extends AbstractHttpTransport {
	private final HttpClient httpClient;

	public DefaultHttpTransport(@NonNull HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public final HttpClient getHttpClient() {
		return httpClient;
	}
}