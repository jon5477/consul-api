package com.ecwid.consul.v1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ecwid.consul.QueryParameters;

public final class Request implements QueryParameters {
	private final String endpoint;
	private final Map<String, String> queryParameters;
	private final byte[] content;
	/**
	 * The Consul token to use, specified as a char[] to prevent string pooling.
	 */
	private final char[] token;

	private Request(Builder b) {
		this.endpoint = b.endpoint;
		this.queryParameters = b.queryParameters;
		this.content = b.content;
		this.token = b.token;
	}

	public String getEndpoint() {
		return endpoint;
	}

	@Override
	public Map<String, String> getQueryParameters() {
		return Collections.unmodifiableMap(queryParameters);
	}

	public byte[] getContent() {
		return content;
	}

	public char[] getToken() {
		return token;
	}

	// -------------------------------
	// Builder
	public static class Builder {
		private String endpoint;
		private final Map<String, String> queryParameters = new HashMap<>();
		private byte[] content;
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
				if (params != null) {
					this.queryParameters.putAll(params.getQueryParameters());
				}
			}
			return this;
		}

		public Builder setContent(byte[] content) {
			this.content = content;
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