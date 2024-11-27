package com.ecwid.consul.transport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.NoopUserTokenHandler;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.nio.AsyncClientConnectionManager;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.transport.TLSConfig.KeyStoreInstanceType;

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
public final class ClientUtils {
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

	private ClientUtils() {
		// static utility class
	}

	/**
	 * Creates an {@link SSLContext} from the given {@link TLSConfig}. This is
	 * provided to interoperate with legacy code.
	 * 
	 * @param tlsConfig The {@link TLSConfig} instance that contains the various
	 *                  configuration options for SSL/TLS.
	 * @return The created {@link SSLContext} from the given {@link TLSConfig}
	 * @throws KeyStoreException         If the keystore could not read for any
	 *                                   reason
	 * @throws FileNotFoundException     If the keystore or truststore file does not
	 *                                   exist
	 * @throws IOException               If an IO exception occurs when reading the
	 *                                   keystore or truststore
	 * @throws NoSuchAlgorithmException  If the algorithm does not exist in this JVM
	 *                                   implementation
	 * @throws CertificateException      If certificates could not be loaded from
	 *                                   the keystore or truststore
	 * @throws UnrecoverableKeyException If an exception occurs while initializing
	 *                                   the keystore.
	 * @throws KeyManagementException    If an exception occurs while constructing
	 *                                   the {@link SSLContext}.
	 */
	public static SSLContext createSSLContext(@NonNull TLSConfig tlsConfig)
			throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException,
			CertificateException, UnrecoverableKeyException, KeyManagementException {
		Objects.requireNonNull(tlsConfig, "TLS configuration cannot be null");
		KeyStore clientStore = KeyStore.getInstance(tlsConfig.getKeyStoreInstanceType().name());
		try (FileInputStream fis = new FileInputStream(tlsConfig.getCertificatePath())) {
			clientStore.load(fis, tlsConfig.getCertificatePassword());
		}
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(clientStore, tlsConfig.getCertificatePassword());
		KeyManager[] kms = kmf.getKeyManagers();
		KeyStore trustStore = KeyStore.getInstance(KeyStoreInstanceType.JKS.name());
		try (FileInputStream fis = new FileInputStream(tlsConfig.getKeyStorePath())) {
			trustStore.load(fis, tlsConfig.getKeyStorePassword());
		}
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(trustStore);
		TrustManager[] tms = tmf.getTrustManagers();
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
		sslContext.init(kms, tms, new SecureRandom());
		return sslContext;
	}

	public static DefaultHttpTransport createDefaultHttpTransport() {
		PoolingHttpClientConnectionManager connectionManager = ClientUtils.createPoolingConnectionManager();
		HttpClient httpClient = ClientUtils.createHttpClient(connectionManager);
		return new DefaultHttpTransport(connectionManager, httpClient);
	}

	public static DefaultHttpTransport createDefaultHttpTransport(
			@Nullable HttpClientConnectionManager connectionManager, @Nullable HttpClient httpClient,
			@Nullable SSLContext sslCtx) {
		HttpClientConnectionManager conMgr = connectionManager;
		if (conMgr == null) {
			conMgr = ClientUtils.createPoolingConnectionManager(sslCtx);
		}
		HttpClient hc = httpClient;
		if (hc == null) {
			hc = ClientUtils.createHttpClient(conMgr);
		}
		return new DefaultHttpTransport(conMgr, hc);
	}

	public static PoolingHttpClientConnectionManager createPoolingConnectionManager() {
		SocketConfig sockCfg = SocketConfig.custom().setSoTimeout(DEFAULT_READ_TIMEOUT).build();
		PoolingHttpClientConnectionManagerBuilder conMgrBuilder = PoolingHttpClientConnectionManagerBuilder.create()
				.setMaxConnTotal(DEFAULT_MAX_CONNECTIONS).setMaxConnPerRoute(DEFAULT_MAX_PER_ROUTE_CONNECTIONS)
				.setDefaultSocketConfig(sockCfg);
		return conMgrBuilder.build();
	}

	public static PoolingHttpClientConnectionManager createPoolingConnectionManager(@Nullable SSLContext sslCtx) {
		SocketConfig sockCfg = SocketConfig.custom().setSoTimeout(DEFAULT_READ_TIMEOUT).build();
		PoolingHttpClientConnectionManagerBuilder conMgrBuilder = PoolingHttpClientConnectionManagerBuilder.create()
				.setMaxConnTotal(DEFAULT_MAX_CONNECTIONS).setMaxConnPerRoute(DEFAULT_MAX_PER_ROUTE_CONNECTIONS)
				.setDefaultSocketConfig(sockCfg);
		if (sslCtx != null) {
			conMgrBuilder.setTlsSocketStrategy(new DefaultClientTlsStrategy(sslCtx));
		}
		return conMgrBuilder.build();
	}

	public static HttpClient createHttpClient(@NonNull HttpClientConnectionManager conMgr) {
		Objects.requireNonNull(conMgr, "connection manager cannot be null");
		RequestConfig reqCfg = RequestConfig.custom().setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT).build();
		HttpClientBuilder hcBuilder = HttpClientBuilder.create().setConnectionManager(conMgr)
				.setDefaultRequestConfig(reqCfg).useSystemProperties()
				.setUserTokenHandler(NoopUserTokenHandler.INSTANCE);
		return hcBuilder.build();
	}

	public static PoolingAsyncClientConnectionManager createPoolingAsyncConnectionManager() {
//		SocketConfig sockCfg = SocketConfig.custom().setSoTimeout(DEFAULT_READ_TIMEOUT).build();
		PoolingAsyncClientConnectionManagerBuilder conMgrBuilder = PoolingAsyncClientConnectionManagerBuilder.create()
				.setMaxConnTotal(DEFAULT_MAX_CONNECTIONS).setMaxConnPerRoute(DEFAULT_MAX_PER_ROUTE_CONNECTIONS);
		return conMgrBuilder.build();
	}

	public static HttpAsyncClient createAsyncHttpClient(@NonNull AsyncClientConnectionManager conMgr) {
		Objects.requireNonNull(conMgr, "connection manager cannot be null");
		RequestConfig reqCfg = RequestConfig.custom().setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT).build();
		HttpAsyncClientBuilder asyncHttpClientBuilder = HttpAsyncClientBuilder.create().setConnectionManager(conMgr)
				.setDefaultRequestConfig(reqCfg).useSystemProperties()
				.setUserTokenHandler(NoopUserTokenHandler.INSTANCE);
		return asyncHttpClientBuilder.build();
	}
}