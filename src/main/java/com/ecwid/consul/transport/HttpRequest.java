package com.ecwid.consul.transport;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class HttpRequest {
	private final String url;
	private final Map<String, String> headers;
	private final char[] token;
	private final byte[] content;

	private HttpRequest(Builder b) {
		this.url = b.url;
		this.headers = b.headers;
		this.token = b.token;
		this.content = b.content;
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

	// ---------------------------------------
	// Builder
	public static final class Builder {
		private String url;
		private Map<String, String> headers = new HashMap<>();
		private char[] token;
		private byte[] content;

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

		@Deprecated(forRemoval = true)
		public Builder setContent(String content) {
			this.content = content != null ? content.getBytes(StandardCharsets.UTF_8) : null;
			return this;
		}

		public Builder setBinaryContent(byte[] binaryContent) {
			this.content = binaryContent;
			return this;
		}

		public HttpRequest build() {
			return new HttpRequest(this);
		}
	}
}