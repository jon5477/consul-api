package com.ecwid.consul.transport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.Objects;

import org.apache.hc.client5.http.async.methods.SimpleBody;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.HeaderGroup;
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
	 * {@link HeaderGroup}.
	 * 
	 * @param group   The {@link HeaderGroup} to add headers to.
	 * @param token   The Consul API token to add as a header, can be {@code null}.
	 * @param headers The key-value {@link Map} of headers to add.
	 */
	static void addHeadersToRequest(HeaderGroup group, char[] token, Map<String, String> headers) {
		Objects.requireNonNull(group, "header group cannot be null");
		if (token != null) {
			group.addHeader(new BasicHeader("X-Consul-Token", CharBuffer.wrap(token), true));
		}
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> headerValue : headers.entrySet()) {
				String name = headerValue.getKey();
				String value = headerValue.getValue();
				group.addHeader(new BasicHeader(name, value));
			}
		}
	}

	/**
	 * Reads the {@link Header} into a {@link String} value.
	 * 
	 * @param header The {@link Header} to read.
	 * @return The {@link Header} as a {@link String} value.
	 */
	@Nullable
	static String getHeaderValue(@Nullable Header header) {
		if (header != null) {
			return header.getValue();
		}
		return null;
	}

	/**
	 * Parses the {@link Header} into a {@link Long} value.
	 * 
	 * @param header The {@link Header} to parse.
	 * @return The {@link Header} as a {@link Long} value.
	 */
	@Nullable
	static Long parseUnsignedLong(@Nullable Header header) {
		if (header != null) {
			String value = header.getValue();
			if (value != null) {
				try {
					return Long.parseUnsignedLong(value);
				} catch (NumberFormatException e) {
					LOGGER.error("Failed to parse header {} as unsigned long", header.getName());
				}
			}
		}
		return null;
	}

	/**
	 * Parses the {@link Header} into a {@link Boolean} value.
	 * 
	 * @param header The {@link Header} to parse.
	 * @return The {@link Header} as a {@link Boolean} value.
	 */
	@Nullable
	static Boolean parseBoolean(@Nullable Header header) {
		if (header != null) {
			if ("true".equals(header.getValue())) {
				return true;
			}
			if ("false".equals(header.getValue())) {
				return false;
			}
			LOGGER.error("Failed to parse header {} as boolean", header.getName());
		}
		return null;
	}

	/**
	 * Parses the given {@link ClassicHttpResponse} or {@link SimpleHttpResponse}
	 * into a {@link HttpResponse}.
	 * 
	 * @param response The Apache HTTP response to parse.
	 * @return A parsed {@link HttpResponse}.
	 * @throws IOException If an {@link IOException} occurs when reading the HTTP
	 *                     response body.
	 */
	static HttpResponse parseResponse(org.apache.hc.core5.http.@NonNull HttpResponse response) throws IOException {
		int statusCode = response.getCode();
		String statusMessage = response.getReasonPhrase();
		// Headers for Consistency Modes
		String consulEffectiveConsistency = getHeaderValue(response.getFirstHeader("X-Consul-Effective-Consistency"));
		Boolean consulKnownLeader = parseBoolean(response.getFirstHeader("X-Consul-KnownLeader"));
		Long consulLastContact = parseUnsignedLong(response.getFirstHeader("X-Consul-LastContact"));
		// Headers for Blocking Queries
		Long consulIndex = parseUnsignedLong(response.getFirstHeader("X-Consul-Index"));
		// Parse the HTTP response body
		JsonNode content = null;
		if (response instanceof ClassicHttpResponse) {
			try (HttpEntity entity = ((ClassicHttpResponse) response).getEntity()) {
				if (entity != null && ContentType.APPLICATION_JSON.getMimeType().equals(entity.getContentType())) {
					try (InputStream is = entity.getContent()) {
						if (is != null) {
							content = JsonUtil.toJsonNode(is);
						}
					}
				}
			}
		} else if (response instanceof SimpleHttpResponse) {
			SimpleBody body = ((SimpleHttpResponse) response).getBody();
			if (body != null && ContentType.APPLICATION_JSON.equals(body.getContentType())) {
				if (body.isText()) {
					content = JsonUtil.toJsonNode(body.getBodyText());
				} else if (body.isBytes()) {
					content = JsonUtil.toJsonNode(body.getBodyBytes());
				}
			}
		} else {
			throw new IllegalArgumentException("unsupported response type: " + response.getClass().getName());
		}
		return new HttpResponse(statusCode, statusMessage, content, consulEffectiveConsistency, consulKnownLeader,
				consulLastContact, consulIndex);
	}
}