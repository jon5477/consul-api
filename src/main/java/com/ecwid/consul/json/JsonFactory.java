package com.ecwid.consul.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides utilities for serializing a POJO (Plain old Java Object) to JSON.
 * 
 * @author Jon Huang (jon5477)
 */
public final class JsonFactory {
	private static final String DESERIALIZE_ERROR = "Unable to deserialize JSON into object";
	private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

	static {
		OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJ_MAPPER.setSerializationInclusion(Include.NON_NULL);
	}

	private JsonFactory() {
		// static utility class
	}

	/**
	 * Serializes the given POJO into JSON as a {@code byte[]}.
	 * 
	 * @param src The POJO to serialize.
	 * @return
	 */
	public static byte[] toBytes(Object src) {
		Objects.requireNonNull(src, "object cannot be null");
		try {
			return OBJ_MAPPER.writeValueAsBytes(src);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to serialize object to JSON", e);
		}
	}

	@Deprecated(forRemoval = true)
	public static String toJson(Object src) {
		try {
			return OBJ_MAPPER.writeValueAsString(src);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to serialize object to JSON", e);
		}
	}

	/**
	 * Reads a {@link JsonNode} from the given {@code byte[]}.
	 * 
	 * @param bytes The {@code byte[]} to read.
	 * @return The parsed {@link JsonNode}.
	 * @throws IOException If an exception occurs while reading.
	 */
	public static JsonNode toJsonNode(byte[] content) throws IOException {
		return OBJ_MAPPER.readTree(content);
	}

	/**
	 * Reads a {@link JsonNode} from the given {@link InputStream}.
	 * 
	 * @param in The {@link InputStream} to read from.
	 * @return The parsed {@link JsonNode}.
	 * @throws IOException If an exception occurs while reading.
	 */
	public static JsonNode toJsonNode(@NonNull InputStream in) throws IOException {
		Objects.requireNonNull(in, "input stream cannot be null");
		return OBJ_MAPPER.readTree(in);
	}

	/**
	 * Converts the given {@link JsonNode} into a POJO type using the provided
	 * {@link TypeReference}.
	 * 
	 * @param <T>  The POJO type.
	 * @param node The {@link JsonNode} to convert.
	 * @param type The {@link TypeReference} to use.
	 */
	public static <T> T toPOJO(@NonNull JsonNode node, @NonNull TypeReference<T> type) {
		Objects.requireNonNull(node, "node cannot be null");
		try {
			return OBJ_MAPPER.treeToValue(node, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(DESERIALIZE_ERROR, e);
		}
	}

	/**
	 * Converts the given {@link JsonNode} into a POJO type using the provided
	 * {@link TypeReference}.
	 * 
	 * @param <T>  The POJO type.
	 * @param node The {@link JsonNode} to convert.
	 * @param type The {@link TypeReference} to use.
	 */
	public static <T> T toPOJO(@NonNull JsonNode node, @NonNull Class<T> type) {
		Objects.requireNonNull(node, "node cannot be null");
		try {
			return OBJ_MAPPER.treeToValue(node, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(DESERIALIZE_ERROR, e);
		}
	}

	@Deprecated(forRemoval = true)
	public static <T> T fromJson(String content, TypeReference<T> type) {
		try {
			return OBJ_MAPPER.readValue(content, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(DESERIALIZE_ERROR, e);
		}
	}

	@Deprecated(forRemoval = true)
	public static <T> T fromJson(String content, Class<T> type) {
		try {
			return OBJ_MAPPER.readValue(content, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(DESERIALIZE_ERROR, e);
		}
	}
}