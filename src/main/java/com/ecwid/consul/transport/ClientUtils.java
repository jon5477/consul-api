/*********************************************************************
* Copyright (c) 2024 Jon Huang
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package com.ecwid.consul.transport;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.transport.TLSConfig.KeyStoreInstanceType;
import com.ecwid.consul.v1.ConsulRawClient;

/**
 * Provides utility functions for interfacing with Java {@link HttpClient}.
 * <ul>
 * <li>Creating {@link SSLContext} for passing to {@link HttpClient}.</li>
 * <li>Creating {@link HttpTransport}s</li>
 * </ul>
 * 
 * @author Jon Huang
 *
 */
public final class ClientUtils {
	/**
	 * The default HTTP connect timeout (30 seconds).
	 */
	public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofSeconds(30);
	/**
	 * The default HTTP request timeout (30 seconds).
	 */
	public static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofSeconds(30);

	private ClientUtils() {
		// static utility class
	}

	/**
	 * Creates an {@link SSLContext} from the given {@link TLSConfig}. This is
	 * provided to interoperate with legacy code.
	 * 
	 * @deprecated Directly pass the {@link SSLContext} to
	 *             {@link ConsulRawClient.Builder#setSSLContext(SSLContext)}.
	 * 
	 * @param tlsConfig The {@link TLSConfig} instance that contains the various
	 *                  configuration options for SSL/TLS.
	 * @return The created {@link SSLContext} from the given {@link TLSConfig}
	 * @throws KeyStoreException         If the keystore could not read for any
	 *                                   reason
	 * @throws IOException               If an IO exception occurs when reading the
	 *                                   keystore or truststore file
	 * @throws NoSuchAlgorithmException  If the algorithm does not exist in this JVM
	 *                                   implementation
	 * @throws CertificateException      If certificates could not be loaded from
	 *                                   the keystore or truststore
	 * @throws UnrecoverableKeyException If an exception occurs while initializing
	 *                                   the keystore.
	 * @throws KeyManagementException    If an exception occurs while constructing
	 *                                   the {@link SSLContext}.
	 */
	@Deprecated(since = "2.0.0", forRemoval = true)
	public static SSLContext createSSLContext(@NonNull TLSConfig tlsConfig) throws KeyStoreException, IOException,
			NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
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
		List<TrustManager> tms = new ArrayList<>(Arrays.asList(tmf.getTrustManagers()));
		tms.add(InsecureTrustManager.INSTANCE);
		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		sslContext.init(kms, tms.toArray(TrustManager[]::new), new SecureRandom());
		return sslContext;
	}

	public static DefaultHttpTransport createDefaultHttpTransport(@Nullable ProxySelector proxy,
			@Nullable SSLContext sslCtx, @Nullable SSLParameters sslParams, @Nullable Executor executor) {
		HttpClient.Builder builder = HttpClient.newBuilder().version(Version.HTTP_1_1) // consul supports HTTP 1.1
				.followRedirects(Redirect.NORMAL).connectTimeout(DEFAULT_CONNECT_TIMEOUT);
		if (proxy != null) {
			builder.proxy(proxy);
		}
		if (sslCtx != null) {
			builder.sslContext(sslCtx);
		}
		if (sslParams != null) {
			builder.sslParameters(sslParams);
		}
		if (executor != null) {
			builder.executor(executor);
		}
		return new DefaultHttpTransport(builder.build());
	}
}