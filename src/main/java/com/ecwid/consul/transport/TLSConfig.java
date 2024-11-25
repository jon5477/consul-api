package com.ecwid.consul.transport;

import javax.net.ssl.SSLContext;

/**
 * @deprecated This class has been deprecated in favor of passing
 *             {@link SSLContext} directly when building the client.
 */
@Deprecated(forRemoval = true)
public final class TLSConfig {
	public enum KeyStoreInstanceType {
		JKS, JCEKS, PKCS12, PKCS11, DKS, BCFKS
	}

	private final KeyStoreInstanceType keyStoreInstanceType;
	private final String certificatePath;
	private final char[] certificatePassword;
	private final String keyStorePath;
	private final char[] keyStorePassword;

	public TLSConfig(KeyStoreInstanceType keyStoreInstanceType, String certificatePath, char[] certificatePassword,
			String keyStorePath, char[] keyStorePassword) {
		this.keyStoreInstanceType = keyStoreInstanceType;
		this.certificatePath = certificatePath;
		this.certificatePassword = certificatePassword;
		this.keyStorePath = keyStorePath;
		this.keyStorePassword = keyStorePassword;
	}

	public KeyStoreInstanceType getKeyStoreInstanceType() {
		return keyStoreInstanceType;
	}

	public String getCertificatePath() {
		return certificatePath;
	}

	public char[] getCertificatePassword() {
		return certificatePassword;
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public char[] getKeyStorePassword() {
		return keyStorePassword;
	}
}