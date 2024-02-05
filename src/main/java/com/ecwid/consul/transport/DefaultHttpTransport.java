package com.ecwid.consul.transport;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;

/**
 * Default HTTP client This class is thread safe
 *
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class DefaultHttpTransport extends AbstractHttpTransport {
	private final HttpClient httpClient;

	public DefaultHttpTransport() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(DEFAULT_MAX_CONNECTIONS);
		connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE_CONNECTIONS);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
				.setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT)
//				.setSocketTimeout(DEFAULT_READ_TIMEOUT)
				.build();
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(connectionManager)
				.setDefaultRequestConfig(requestConfig).useSystemProperties();
		this.httpClient = httpClientBuilder.build();
	}

	public DefaultHttpTransport(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	protected HttpClient getHttpClient() {
		return httpClient;
	}
}