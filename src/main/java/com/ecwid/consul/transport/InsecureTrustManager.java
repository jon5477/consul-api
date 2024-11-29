package com.ecwid.consul.transport;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public final class InsecureTrustManager implements X509TrustManager {
	public static final InsecureTrustManager INSTANCE = new InsecureTrustManager();

	private InsecureTrustManager() {
		// singleton
	}

	@Override
	public final void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// No client certificate verification
	}

	@Override
	public final void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// No server certificate verification
	}

	@Override
	public final X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}
}