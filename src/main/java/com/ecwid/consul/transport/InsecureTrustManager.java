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

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Provides an insecure {@link X509TrustManager} implementation that does not
 * verify the certificates or hostname.
 * 
 * @deprecated This is only kept as a compatibility layer for {@link TLSConfig}.
 *             You should never use this in a production environment because it
 *             will never validate certificates or hostnames thus exposing
 *             yourself to MITM attacks.
 * 
 * @author Jon Huang (jon5477)
 *
 */
@Deprecated(since = "2.0.0", forRemoval = true)
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