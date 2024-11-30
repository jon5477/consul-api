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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.json.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Provides various HTTP related utility functions.
 * 
 * @author Jon Huang
 *
 */
final class HttpUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

	private HttpUtils() {
		// static utility class
	}

	/**
	 * Creates a full {@link URI} to the Consul API endpoint.
	 * 
	 * @param agentUri    The {@link URI} to the Consul agent.
	 * @param endpoint    The Consul API endpoint to use.
	 * @param queryParams The query parameters to include.
	 * @return The {@link URI} to the Consul API endpoint including query
	 *         parameters.
	 */
	static URI buildURI(@NonNull URI agentUri, @NonNull String endpoint, QueryParameters... queryParams) {
		Objects.requireNonNull(endpoint, "endpoint cannot be null");
		try {
			URLEncoder.encode(endpoint, StandardCharsets.UTF_8);
			String queryStr = Arrays.stream(queryParams).flatMap(e -> e.getQueryParameters().entrySet().stream())
					.map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
			String query = !queryStr.isEmpty() ? queryStr : null;
			return new URI(agentUri.getScheme(), agentUri.getUserInfo(), agentUri.getHost(), agentUri.getPort(),
					agentUri.getPath() + endpoint, query, null);
		} catch (URISyntaxException e) {
			throw new ConsulException(e);
		}
	}

	/**
	 * Appends the given HTTP headers and Consul API token to the
	 * {@link HttpRequest.Builder}
	 * 
	 * @param request The {@link HttpRequest.Builder} to add headers to.
	 * @param token   The Consul API token to add as a header, can be {@code null}.
	 * @param headers The key-value {@link Map} of headers to add.
	 */
	static void addHeadersToRequest(HttpRequest.@NonNull Builder request, char[] token,
			@NonNull Map<String, String> headers) {
		Objects.requireNonNull(request, "request builder cannot be null");
		Objects.requireNonNull(headers, "headers cannot be null");
		if (token != null) {
			request.header("X-Consul-Token", new String(token));
		}
		if (!headers.isEmpty()) {
			for (Map.Entry<String, String> headerValue : headers.entrySet()) {
				String name = headerValue.getKey();
				String value = headerValue.getValue();
				request.header(name, value);
			}
		}
	}

	/**
	 * Parses the HTTP header by name into a {@link Long} value.
	 * 
	 * @param headers    The {@link HttpHeaders} instance.
	 * @param headerName The HTTP header name.
	 * @return The HTTP header value parsed as a {@link Long}.
	 */
	@Nullable
	static Long parseUnsignedLong(@NonNull HttpHeaders headers, @NonNull String headerName) {
		Optional<String> opt = headers.firstValue(headerName);
		if (opt.isPresent()) {
			String value = opt.get();
			if (value != null) {
				try {
					return Long.parseUnsignedLong(value);
				} catch (NumberFormatException e) {
					LOGGER.error("Failed to parse header {} as unsigned long", headerName);
				}
			}
		}
		return null;
	}

	/**
	 * Parses the HTTP header by name into a {@link Boolean} value.
	 * 
	 * @param headers    The {@link HttpHeaders} instance.
	 * @param headerName The HTTP header name.
	 * @return The HTTP header value parsed as a {@link Boolean}.
	 */
	@Nullable
	static Boolean parseBoolean(@NonNull HttpHeaders headers, @NonNull String headerName) {
		Optional<String> opt = headers.firstValue(headerName);
		if (opt.isPresent()) {
			if ("true".equals(opt.get())) {
				return true;
			}
			if ("false".equals(opt.get())) {
				return false;
			}
			LOGGER.error("Failed to parse header {} as boolean", headerName);
		}
		return null;
	}

	/**
	 * Parses the given {@link HttpResponse} into a {@link ConsulHttpResponse}.
	 * 
	 * @param response The {@link HttpResponse} to parse.
	 * @return The parsed {@link ConsulHttpResponse}.
	 * @throws IOException If an {@link IOException} occurs when reading the HTTP
	 *                     response body.
	 */
	@NonNull
	static ConsulHttpResponse parseResponse(@NonNull HttpResponse<InputStream> response) throws IOException {
		int statusCode = response.statusCode();
		HttpHeaders headers = response.headers();
		// Headers for Consistency Modes
		String consulEffectiveConsistency = headers.firstValue("X-Consul-Effective-Consistency").orElse(null);
		Boolean consulKnownLeader = parseBoolean(headers, "X-Consul-KnownLeader");
		Long consulLastContact = parseUnsignedLong(headers, "X-Consul-LastContact");
		// Headers for Blocking Queries
		Long consulIndex = parseUnsignedLong(headers, "X-Consul-Index");
		String contentType = headers.firstValue("Content-Type").orElse(null);
		if ("application/json".equals(contentType)) {
			try (InputStream is = response.body()) {
				JsonNode content = JsonUtil.toJsonNode(is);
				return new ConsulHttpResponse(statusCode, content, consulEffectiveConsistency, consulKnownLeader,
						consulLastContact, consulIndex);
			}
		}
		return new ConsulHttpResponse(statusCode, null, consulEffectiveConsistency, consulKnownLeader,
				consulLastContact, consulIndex);
	}
}