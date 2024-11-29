package com.ecwid.consul.transport;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public final class ConsulHttpRequest {
	private final URI uri;
	private final Map<String, String> headers;
	private final char[] token;
	private final byte[] content;
	/**
	 * The "Content-Type" HTTP header, usually this value is
	 * {@value ContentType#APPLICATION_JSON}.
	 */
	private final String contentType;

	private ConsulHttpRequest(Builder b) {
		this.uri = b.uri;
		this.headers = b.headers;
		this.token = b.token;
		this.content = b.content;
		this.contentType = b.contentType;
	}

	public URI getURI() {
		return uri;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public char[] getToken() {
		return token;
	}

	public byte[] getContent() {
		return content;
	}

	public String getContentType() {
		return contentType;
	}

	// ---------------------------------------
	// Builder
	public static final class Builder {
		private URI uri;
		private Map<String, String> headers = new HashMap<>();
		private char[] token;
		private byte[] content;
		private String contentType;

		public Builder setURI(URI uri) {
			this.uri = uri;
			return this;
		}

		public Builder addHeaders(Map<String, String> headers) {
			this.headers.putAll(headers);
			return this;
		}

		public Builder addHeader(String name, String value) {
			this.headers.put(name, value);
			return this;
		}

		public Builder setToken(char[] token) {
			this.token = token;
			return this;
		}

		public Builder setContent(byte[] content) {
			this.content = content;
			return this;
		}

		public Builder setContentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public ConsulHttpRequest build() {
			return new ConsulHttpRequest(this);
		}
	}
}