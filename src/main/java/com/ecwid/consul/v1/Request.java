package com.ecwid.consul.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ecwid.consul.QueryParameters;

public final class Request {
	private final String endpoint;
	private final List<QueryParameters> queryParameters;
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
		this.queryParameters = b.urlParameters;
		this.content = b.content;
		this.binaryContent = b.binaryContent;
		this.token = b.token;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public List<QueryParameters> getQueryParameters() {
		return queryParameters;
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
		private List<QueryParameters> urlParameters = new ArrayList<>();
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

		public Builder addUrlParameters(Collection<QueryParameters> urlParameters) {
			this.urlParameters.addAll(urlParameters);
			return this;
		}

		public Builder addUrlParameters(QueryParameters... urlParameters) {
			this.urlParameters.addAll(Arrays.asList(urlParameters));
			return this;
		}

		public Builder addUrlParameter(QueryParameters urlParameter) {
			this.urlParameters.add(urlParameter);
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
