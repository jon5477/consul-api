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
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Provides utilities for serializing a POJO (Plain old Java Object) to JSON.
 * 
 * @author Jon Huang (jon5477)
 */
public final class JsonUtil {
	private static final String NODE_NOT_NULL_MSG = "node cannot be null";
	private static final String DESERIALIZE_ERROR = "Unable to deserialize JSON into object";
	private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

	static {
		OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJ_MAPPER.setSerializationInclusion(Include.NON_NULL);
	}

	private JsonUtil() {
		// static utility class
	}

	/**
	 * Serializes the given POJO into JSON as a {@code byte[]}.
	 * 
	 * @param src The POJO to serialize.
	 * @return The serialized POJO as a JSON {@code byte[]}.
	 */
	public static byte[] toBytes(@NonNull Object src) {
		Objects.requireNonNull(src, "object cannot be null");
		try {
			return OBJ_MAPPER.writeValueAsBytes(src);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to serialize object to JSON", e);
		}
	}

	/**
	 * Serializes the given {@link JsonNode} into JSON as a {@code byte[]}.
	 * 
	 * @param node The {@link JsonNode} to serialize.
	 * @return The serialized {@link JsonNode} as a JSON {@code byte[]}.
	 */
	public static byte[] toBytes(@NonNull JsonNode node) {
		Objects.requireNonNull(node, NODE_NOT_NULL_MSG);
		try {
			ObjectWriter writer = OBJ_MAPPER.writer();
			return writer.writeValueAsBytes(node);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to serialize object to JSON", e);
		}
	}

	/**
	 * Reads a {@link JsonNode} from the given {@link String}.
	 * 
	 * @param content The {@link String} to read.
	 * @return The parsed {@link JsonNode}.
	 */
	public static JsonNode toJsonNode(@NonNull String content) {
		Objects.requireNonNull(content, "content cannot be null");
		try {
			return OBJ_MAPPER.readTree(content);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(DESERIALIZE_ERROR, e);
		}
	}

	/**
	 * Reads a {@link JsonNode} from the given {@code byte[]}.
	 * 
	 * @param content The {@code byte[]} to read.
	 * @return The parsed {@link JsonNode}.
	 */
	public static JsonNode toJsonNode(byte[] content) {
		try {
			return OBJ_MAPPER.readTree(content);
		} catch (IOException e) {
			throw new RuntimeException(DESERIALIZE_ERROR, e);
		}
	}

	/**
	 * Reads a {@link JsonNode} from the given {@link InputStream}.
	 * 
	 * @param in The {@link InputStream} to read from.
	 * @return The parsed {@link JsonNode}.
	 */
	@SuppressWarnings("resource")
	public static JsonNode toJsonNode(@NonNull InputStream in) {
		Objects.requireNonNull(in, "input stream cannot be null");
		try {
			return OBJ_MAPPER.readTree(in);
		} catch (IOException e) {
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
	public static <T> T toPOJO(@NonNull JsonNode node, @NonNull TypeReference<T> type) {
		Objects.requireNonNull(node, NODE_NOT_NULL_MSG);
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
		Objects.requireNonNull(node, NODE_NOT_NULL_MSG);
		try {
			return OBJ_MAPPER.treeToValue(node, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(DESERIALIZE_ERROR, e);
		}
	}
}