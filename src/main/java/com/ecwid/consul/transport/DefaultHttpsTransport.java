package com.ecwid.consul.transport;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;

import com.ecwid.consul.transport.TLSConfig.KeyStoreInstanceType;

/**
 * Default HTTPS client This class is thread safe
 *
 * @author Carlos Augusto Ribeiro Mantovani (gutomantovani@gmail.com)
 */
public final class DefaultHttpsTransport extends AbstractHttpTransport {
	private final HttpClient httpClient;

	public DefaultHttpsTransport(TLSConfig tlsConfig) {
		try {
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
			SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("https", factory).build();
			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
			connectionManager.setMaxTotal(DEFAULT_MAX_CONNECTIONS);
			connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE_CONNECTIONS);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
					.setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT)
					// .setSocketTimeout(DEFAULT_READ_TIMEOUT)
					.build();
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig);
			this.httpClient = httpClientBuilder.build();
		} catch (GeneralSecurityException | IOException e) {
			throw new TransportException(e);
		}
	}

	public DefaultHttpsTransport(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	protected HttpClient getHttpClient() {
		return httpClient;
	}
}