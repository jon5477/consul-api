package com.ecwid.consul.v1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ecwid.consul.QueryParameters;

public final class Request implements QueryParameters {
	private final String endpoint;
	private final Map<String, String> queryParameters;
	private final String content;
	private final byte[] binaryContent;
	/**
	 * The Consul token to use, specified as a char[] to prevent string pooling.
	 */
	private final char[] token;

	private Request(Builder b) {
		if (b.content != null && b.binaryContent != null) {
			throw new IllegalArgumentException("You should set only content or binaryContent, not both.");
		}
		this.endpoint = b.endpoint;
		this.queryParameters = b.queryParameters;
		this.content = b.content;
		this.binaryContent = b.binaryContent;
		this.token = b.token;
	}

	public String getEndpoint() {
		return endpoint;
	}

	@Override
	public Map<String, String> getQueryParameters() {
		return Collections.unmodifiableMap(queryParameters);
	}

	@Deprecated(forRemoval = true) // It is better to keep content in binary form
	public String getContent() {
		return content;
	}

	public byte[] getBinaryContent() {
		return binaryContent;
	}

	public char[] getToken() {
		return token;
	}

	// -------------------------------
	// Builder
	public static class Builder {
		private String endpoint;
		private final Map<String, String> queryParameters = new HashMap<>();
		private String content;
		private byte[] binaryContent;
		private char[] token;

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder setEndpoint(String endpoint) {
			this.endpoint = endpoint;
			return this;
		}

		public Builder addQueryParameter(String key, String value) {
			this.queryParameters.put(key, value);
			return this;
		}

		public Builder addQueryParameters(Map<String, String> queryParameters) {
			this.queryParameters.putAll(queryParameters);
			return this;
		}

		public Builder addQueryParameters(QueryParameters... queryParameters) {
			for (QueryParameters params : queryParameters) {
				this.queryParameters.putAll(params.getQueryParameters());
			}
			return this;
		}

		@Deprecated(forRemoval = true)
		public Builder setContent(String content) {
			this.content = content;
			return this;
		}

		public Builder setBinaryContent(byte[] binaryContent) {
			this.binaryContent = binaryContent;
			return this;
		}

		public Builder setToken(char[] token) {
			this.token = token;
			return this;
		}

		public Request build() {
			return new Request(this);
		}
	}
}
