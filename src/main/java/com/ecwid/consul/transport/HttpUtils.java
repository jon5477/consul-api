package com.ecwid.consul.transport;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * Appends the given HTTP headers and Consul API token to the
	 * {@link HttpRequest.Builder}
	 * 
	 * @param request The {@link HttpRequest.Builder} to add headers to.
	 * @param token   The Consul API token to add as a header, can be {@code null}.
	 * @param headers The key-value {@link Map} of headers to add.
	 */
	static void addHeadersToRequest(@NonNull HttpRequest.Builder request, char[] token,
			@NonNull Map<String, String> headers) {
		Objects.requireNonNull(request, "request builder cannot be null");
		if (token != null) {
			request.setHeader("X-Consul-Token", new String(token));
		}
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> headerValue : headers.entrySet()) {
				String name = headerValue.getKey();
				String value = headerValue.getValue();
				request.setHeader(name, value);
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
		if (opt != null) {
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
	static ConsulHttpResponse parseResponse(HttpResponse<InputStream> response) throws IOException {
		int statusCode = response.statusCode();
		HttpHeaders headers = response.headers();
		// Headers for Consistency Modes
		String consulEffectiveConsistency = headers.firstValue("X-Consul-Effective-Consistency").orElse(null);
		Boolean consulKnownLeader = parseBoolean(headers, "X-Consul-KnownLeader");
		Long consulLastContact = parseUnsignedLong(headers, "X-Consul-LastContact");
		// Headers for Blocking Queries
		Long consulIndex = parseUnsignedLong(headers, "X-Consul-Index");
		try (InputStream is = response.body()) {
			JsonNode content = JsonUtil.toJsonNode(is);
			return new ConsulHttpResponse(statusCode, content, consulEffectiveConsistency, consulKnownLeader,
					consulLastContact, consulIndex);
		}
	}
}