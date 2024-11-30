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

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a HTTP request to be made to Consul. Contains all the relevant and
 * necessary fields for making an HTTP request.
 * 
 * @author Jon Huang (jon5477)
 *
 */
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

	@NonNull
	public final URI getURI() {
		return uri;
	}

	@NonNull
	public final Map<String, String> getHeaders() {
		return headers;
	}

	public final char[] getToken() {
		return token;
	}

	public final byte[] getContent() {
		return content;
	}

	@Nullable
	public final String getContentType() {
		return contentType;
	}

	public static final class Builder {
		private final URI uri;
		private final Map<String, String> headers = new HashMap<>();
		private char[] token;
		private byte[] content;
		private String contentType;

		public Builder(@NonNull URI uri) {
			this.uri = Objects.requireNonNull(uri, "URI cannot be null");
		}

		public final Builder addHeaders(@NonNull Map<String, String> headers) {
			Objects.requireNonNull(headers, "headers cannot be null");
			this.headers.putAll(headers);
			return this;
		}

		public final Builder addHeader(@NonNull String name, @Nullable String value) {
			Objects.requireNonNull(name, "header name cannot be null");
			this.headers.put(name, value);
			return this;
		}

		public final Builder setToken(char[] token) {
			this.token = token;
			return this;
		}

		public final Builder setContent(byte[] content) {
			this.content = content;
			return this;
		}

		public final Builder setContentType(@Nullable String contentType) {
			this.contentType = contentType;
			return this;
		}

		public final ConsulHttpRequest build() {
			return new ConsulHttpRequest(this);
		}
	}
}