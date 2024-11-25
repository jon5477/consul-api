package com.ecwid.consul.transport;

import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;

/**
 * Default HTTP client This class is thread safe
 *
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class DefaultHttpTransport extends AbstractHttpTransport {
	// TODO Maybe we should stick with async client only?
	private final HttpClient httpClient;
	private final HttpAsyncClient asyncHttpClient;

	public DefaultHttpTransport() {
		this(null, null);
	}

	public DefaultHttpTransport(HttpClient httpClient, HttpAsyncClient asyncHttpClient) {
		if (httpClient == null) {
			ConnectionConfig connConfig = ConnectionConfig.custom().setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
					.setSocketTimeout(DEFAULT_READ_TIMEOUT).build();
			// TODO Move this into the instance field
			PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
					.setDefaultConnectionConfig(connConfig).setMaxConnTotal(DEFAULT_MAX_CONNECTIONS)
					.setMaxConnPerRoute(DEFAULT_MAX_PER_ROUTE_CONNECTIONS).build();
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT)
					.build();
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).useSystemProperties();
			this.httpClient = httpClientBuilder.build();
		} else {
			this.httpClient = httpClient;
		}
		if (asyncHttpClient == null) {
			ConnectionConfig connConfig = ConnectionConfig.custom().setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
					.setSocketTimeout(DEFAULT_READ_TIMEOUT).build();
			// TODO Move this into the instance field
			PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
					.setDefaultConnectionConfig(connConfig).setMaxConnTotal(DEFAULT_MAX_CONNECTIONS)
					.setMaxConnPerRoute(DEFAULT_MAX_PER_ROUTE_CONNECTIONS).build();
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT)
					.build();
			HttpAsyncClientBuilder asyncHttpClientBuilder = HttpAsyncClientBuilder.create()
					.setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig)
					.useSystemProperties();
			this.asyncHttpClient = asyncHttpClientBuilder.build();
		} else {
			this.asyncHttpClient = asyncHttpClient;
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