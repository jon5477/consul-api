package com.ecwid.consul.transport;

import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.nio.AsyncClientConnectionManager;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default HTTP client This class is thread safe
 *
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class DefaultHttpTransport extends AbstractHttpTransport {
	// TODO Maybe we should stick with async client only?
	private final HttpClientConnectionManager connectionManager;
	private final HttpClient httpClient;
	private final AsyncClientConnectionManager asyncConnectionManager;
	private final HttpAsyncClient asyncHttpClient;

	public DefaultHttpTransport() {
		this(null, null, null, null);
	}

	public DefaultHttpTransport(@Nullable HttpClientConnectionManager connectionManager,
			@Nullable HttpClient httpClient, @Nullable AsyncClientConnectionManager asyncConnectionManager,
			@Nullable HttpAsyncClient asyncHttpClient) {
		if (connectionManager != null && httpClient != null) {
			this.connectionManager = connectionManager;
			this.httpClient = httpClient;
		} else {
			PoolingHttpClientConnectionManager conMgr = ClientUtils.createPoolingConnectionManager();
			this.connectionManager = conMgr;
			this.httpClient = ClientUtils.createHttpClient(conMgr);
		}
		if (asyncConnectionManager != null && asyncHttpClient != null) {
			this.asyncConnectionManager = asyncConnectionManager;
			this.asyncHttpClient = asyncHttpClient;
		} else {
			PoolingAsyncClientConnectionManager conMgr = ClientUtils.createPoolingAsyncConnectionManager();
			this.asyncConnectionManager = conMgr;
			this.asyncHttpClient = ClientUtils.createAsyncHttpClient(conMgr);
		}
	}

	@Override
	protected final HttpClient getHttpClient() {
		return httpClient;
	}

	@Override
	protected final HttpAsyncClient getAsyncHttpClient() {
		return asyncHttpClient;
	}
}