package com.ecwid.consul.transport;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.hc.core5.http.ContentType;

public final class HttpRequest {
	private final String url;
	private final Map<String, String> headers;
	private final char[] token;
	private final byte[] content;
	/**
	 * The "Content-Type" HTTP header, usually this value is
	 * {@value ContentType#APPLICATION_JSON}.
	 */
	private final String contentType;

	private HttpRequest(Builder b) {
		this.url = b.url;
		this.headers = b.headers;
		this.token = b.token;
		this.content = b.content;
		this.contentType = b.contentType;
	}

	public String getUrl() {
		return url;
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
		private String url;
		private Map<String, String> headers = new HashMap<>();
		private char[] token;
		private byte[] content;
		private String contentType;

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder setUrl(String url) {
			this.url = url;
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

		/**
		 * This has been deprecated in favor of {@link #setContent(byte[])}.
		 * 
		 * @param content The string based content to set.
		 * @return This {@link Builder} instance for chaining.
		 */
		@Deprecated(forRemoval = true)
		public Builder setContent(String content) {
			this.content = content != null ? content.getBytes(StandardCharsets.UTF_8) : null;
			return this;
		}

		public Builder setContent(byte[] binaryContent) {
			this.content = binaryContent;
			return this;
		}

		public Builder setContentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public HttpRequest build() {
			return new HttpRequest(this);
		}
	}
}