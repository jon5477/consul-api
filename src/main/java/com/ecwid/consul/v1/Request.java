package com.ecwid.consul.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.QueryParameters;

/**
 * Represents a HTTP request to be made to Consul. Contains all the relevant and
 * necessary fields for making an HTTP request.
 * 
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class Request {
	/**
	 * The Consul API endpoint to call.
	 */
	private final String endpoint;
	/**
	 * The HTTP headers to add.
	 */
	private final Map<String, String> headers;
	/**
	 * The HTTP query parameters to apply.
	 */
	private final List<QueryParameters> queryParameters;
	/**
	 * The extra HTTP query parameters to apply.
	 */
	private final Map<String, String> extraQueryParameters;
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
	private char[] token;

	private Request(Builder b) {
		this.endpoint = b.endpoint;
		this.headers = b.headers;
		this.queryParameters = b.queryParameters;
		this.extraQueryParameters = b.extraQueryParameters;
		this.content = b.content;
		this.contentType = b.contentType;
		this.token = b.token;
	}

	public final String getEndpoint() {
		return endpoint;
	}

	public final Map<String, String> getHeaders() {
		return Collections.unmodifiableMap(headers);
	}

	public final List<QueryParameters> getQueryParameters() {
		return Collections.unmodifiableList(queryParameters);
	}

	public final Map<String, String> getExtraQueryParameters() {
		return Collections.unmodifiableMap(extraQueryParameters);
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

	public final void setToken(char[] token) {
		this.token = token;
	}

	public static final class Builder {
		private final String endpoint;
		private final Map<String, String> headers = new HashMap<>();
		private final List<QueryParameters> queryParameters = new ArrayList<>();
		private final Map<String, String> extraQueryParameters = new HashMap<>();
		private byte[] content;
		private String contentType;
		private char[] token;

		public Builder(@NonNull String endpoint) {
			Objects.requireNonNull(endpoint, "endpoint cannot be null");
			this.endpoint = endpoint;
		}

		public Builder addHeader(@NonNull String key, @NonNull String value) {
			this.headers.put(key, value);
			return null;
		}

		public Builder addHeaders(@NonNull Map<String, String> headers) {
			this.headers.putAll(headers);
			return this;
		}

		public Builder addQueryParameters(QueryParameters... queryParameters) {
			for (QueryParameters params : queryParameters) {
				if (params != null) {
					this.queryParameters.add(params);
				}
			}
			return this;
		}

		public Builder addQueryParameter(@NonNull String key, @Nullable String value) {
			this.extraQueryParameters.put(key, value);
			return this;
		}

		public Builder addQueryParameters(@NonNull Map<String, String> queryParameters) {
			this.extraQueryParameters.putAll(queryParameters);
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