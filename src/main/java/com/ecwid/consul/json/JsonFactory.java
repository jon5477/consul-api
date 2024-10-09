package com.ecwid.consul.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Provides utilities for serializing a POJO (Plain old Java Object) to JSON.
 * 
 * @author Jon Huang (jon5477)
 *
 */
public final class JsonFactory {
	private static final String SERIALIZE_ERROR = "Unable to serialize object from JSON";
	private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

	static {
		OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJ_MAPPER.setSerializationInclusion(Include.NON_NULL);
	}

	private JsonFactory() {
		// static utility class
	}

	public static String toJson(Object src) {
		try {
			return OBJ_MAPPER.writeValueAsString(src);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to serialize object to JSON", e);
		}
	}

	public static <T> T fromJson(String content, TypeReference<T> type) {
		try {
			return OBJ_MAPPER.readValue(content, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(SERIALIZE_ERROR, e);
		}
	}

	public static <T> T fromJson(String content, Class<T> type) {
		try {
			return OBJ_MAPPER.readValue(content, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(SERIALIZE_ERROR, e);
		}
	}
}