package com.ecwid.consul.transport;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;

/**
 * Default HTTPS client This class is thread safe
 *
 * @author Carlos Augusto Ribeiro Mantovani (gutomantovani@gmail.com)
 */
public final class DefaultHttpsTransport extends AbstractHttpTransport {
	private final HttpClient httpClient;

	public DefaultHttpsTransport(TLSConfig tlsConfig) {
//		final SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustAllStrategy()).build();
		SSLContext sslCtx = null;
		final SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
				.setSslContext(sslCtx).build();
		final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
				.setSSLSocketFactory(sslSocketFactory).build();
		this.httpClient = HttpClients.custom().setConnectionManager(cm).evictExpiredConnections().build();
//		try {
//			KeyStore clientStore = KeyStore.getInstance(tlsConfig.getKeyStoreInstanceType().name());
//			try (FileInputStream fis = new FileInputStream(tlsConfig.getCertificatePath())) {
//				clientStore.load(fis, tlsConfig.getCertificatePassword());
//			}
//			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//			kmf.init(clientStore, tlsConfig.getCertificatePassword());
//			KeyManager[] kms = kmf.getKeyManagers();
//			KeyStore trustStore = KeyStore.getInstance(KeyStoreInstanceType.JKS.name());
//			try (FileInputStream fis = new FileInputStream(tlsConfig.getKeyStorePath())) {
//				trustStore.load(fis, tlsConfig.getKeyStorePassword());
//			}
//			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//			tmf.init(trustStore);
//			TrustManager[] tms = tmf.getTrustManagers();
//			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
//			sslContext.init(kms, tms, new SecureRandom());
//			SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext);
//			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
//					.register("https", factory).build();
//			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
//			connectionManager.setMaxTotal(DEFAULT_MAX_CONNECTIONS);
//			connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE_CONNECTIONS);
//			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
//					.setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT)
//					// .setSocketTimeout(DEFAULT_READ_TIMEOUT)
//					.build();
//			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(connectionManager)
//					.setDefaultRequestConfig(requestConfig);
//			this.httpClient = httpClientBuilder.build();
//		} catch (GeneralSecurityException | IOException e) {
//			throw new TransportException(e);
//		}
	}

	public DefaultHttpsTransport(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public DefaultHttpsTransport(SSLContext sslCtx, HttpClient httpClient2, HttpAsyncClient asyncHttpClient) {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected HttpClient getHttpClient() {
		return httpClient;
	}

	@Override
	protected HttpAsyncClient getAsyncHttpClient() {
		// TODO Auto-generated method stub
		return null;
	}
}