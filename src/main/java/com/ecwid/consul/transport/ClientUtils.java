package com.ecwid.consul.transport;

import java.util.Objects;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides utility functions for interfacing with Apache HTTP Client 5.x.
 * <ul>
 * <li>Creating HTTP clients</li>
 * <li>Creating HTTP requests</li>
 * <li>Creating HTTP headers</li>
 * </ul>
 * 
 * @author Jon Huang
 *
 */
public class ClientUtils {
	/**
	 * The default number of maximum connections in the pool.
	 */
	private static final int DEFAULT_MAX_CONNECTIONS = 1000;
	/**
	 * The default number of maximum connections per route in the pool.
	 */
	private static final int DEFAULT_MAX_PER_ROUTE_CONNECTIONS = 500;
	/**
	 * The default HTTP connection timeout (10 seconds).
	 */
	private static final Timeout DEFAULT_CONNECTION_TIMEOUT = Timeout.ofSeconds(10);
	/**
	 * The default HTTP read timeout of 10 minutes. This is account for blocking
	 * queries.
	 * 
	 * @see https://developer.hashicorp.com/consul/api-docs/features/blocking
	 */
	private static final Timeout DEFAULT_READ_TIMEOUT = Timeout.ofMinutes(10);

	public static PoolingHttpClientConnectionManager createPoolingConnectionManager() {
		SocketConfig sockCfg = SocketConfig.custom().setSoTimeout(DEFAULT_READ_TIMEOUT).build();
		PoolingHttpClientConnectionManagerBuilder conMgrBuilder = PoolingHttpClientConnectionManagerBuilder.create()
				.setMaxConnTotal(DEFAULT_MAX_CONNECTIONS).setMaxConnPerRoute(DEFAULT_MAX_PER_ROUTE_CONNECTIONS)
				.setDefaultSocketConfig(sockCfg);
		return conMgrBuilder.build();
	}

	public static HttpClient createHttpClient(@NonNull HttpClientConnectionManager conMgr) {
		Objects.requireNonNull(conMgr, "connection manager cannot be null");
		RequestConfig reqCfg = RequestConfig.custom().setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT).build();
		HttpClientBuilder hcBuilder = HttpClientBuilder.create().setConnectionManager(conMgr)
				.setDefaultRequestConfig(reqCfg);
		return hcBuilder.build();
	}
}