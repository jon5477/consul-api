package com.ecwid.consul.v1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.QueryParameters;

public final class Request implements QueryParameters {
	private final String endpoint;
	private final Map<String, String> queryParameters;
	/**
	 * The HTTP request content as a {@code byte[]}.
	 */
	private final byte[] content;
	/**
	 * The HTTP request content type.
	 */
	private final String contentType;
	/**
	 * The Consul token to use, specified as a char[] to prevent string pooling.
	 */
	private final char[] token;

	private Request(Builder b) {
		this.endpoint = b.endpoint;
		this.queryParameters = b.queryParameters;
		this.content = b.content;
		this.contentType = b.contentType;
		this.token = b.token;
	}

	public final String getEndpoint() {
		return endpoint;
	}

	@Override
	public final Map<String, String> getQueryParameters() {
		return Collections.unmodifiableMap(queryParameters);
	}

	public final byte[] getContent() {
		return content;
	}

	public final String getContentType() {
		return contentType;
	}

	public final char[] getToken() {
		return token;
	}

	public static final class Builder {
		private final String endpoint;
		private final Map<String, String> queryParameters = new HashMap<>();
		private byte[] content;
		private String contentType;
		private char[] token;

		public Builder(@NonNull String endpoint) {
			Objects.requireNonNull(endpoint, "endpoint cannot be null");
			this.endpoint = endpoint;
		}

		public Builder addQueryParameter(@NonNull String key, @Nullable String value) {
			this.queryParameters.put(key, value);
			return this;
		}

		public Builder addQueryParameters(@NonNull Map<String, String> queryParameters) {
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

		public Builder setContentType(@Nullable String contentType) {
			this.contentType = contentType;
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