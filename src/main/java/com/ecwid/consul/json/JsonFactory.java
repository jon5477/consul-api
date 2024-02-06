package com.ecwid.consul.json;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Provides utilities for serializing a POJO (Plain old Java Object) to JSON.
 * 
 * @author Jon Huang (jon5477)
 */
public final class JsonFactory {
	private static final String SERIALIZE_ERROR = "Unable to serialize object from JSON";
	private static final String SER_TYPE_ERROR = "Unhandled JSON serialization type: ";

	/**
	 * The JSON serializer types that is used to serialize POJOs to JSON.
	 */
	private enum JsonSerializerType {
		JACKSON, GSON;
	}

	private static final AtomicReference<JsonSerializerType> typeRef = new AtomicReference<>();
	private static final AtomicReference<Gson> gsonRef = new AtomicReference<>();
	private static final AtomicReference<ObjectMapper> objMapperRef = new AtomicReference<>();

	private JsonFactory() {
		// static utility class
	}

	private static void init() {
		if (typeRef.get() == null) {
			try {
				Class<?> jacksonClass = Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
				ObjectMapper objMapper = (ObjectMapper) jacksonClass.getDeclaredConstructor().newInstance();
				objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				objMapperRef.set(objMapper);
				typeRef.set(JsonSerializerType.JACKSON);
				return;
			} catch (ClassNotFoundException e) {
				// jackson library not found, fallback to use Gson
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// jackson library could not be instantiated, fallback to use Gson
			}
			try {
				Class<?> gsonClass = Class.forName("com.google.gson.Gson");
				gsonRef.set((Gson) gsonClass.getDeclaredConstructor().newInstance());
				typeRef.set(JsonSerializerType.GSON);
			} catch (ClassNotFoundException e) {
				// gson library not found - we can't operate this library without it
				throw new Error(
						"Could not locate the Jackson or GSON json libraries, one must be present to use the consul-api library");
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// gson library could not be instantiated, just bubble the exception
				throw new RuntimeException(e);
			}
		}
	}

	public static String toJson(Object src) {
		// Initialize the JSON serializers if needed
		init();
		switch (typeRef.get()) {
			case JACKSON:
				try {
					return objMapperRef.get().writeValueAsString(src);
				} catch (JsonProcessingException e) {
					throw new RuntimeException("Unable to serialize object to JSON", e);
				}
			case GSON:
				return gsonRef.get().toJson(src);
			default:
				throw new AssertionError(SER_TYPE_ERROR + typeRef.get());
		}
	}

	public static <T> T fromJson(String content, TypeReference<T> type) {
		// Initialize the JSON serializers if needed
		init();
		switch (typeRef.get()) {
			case JACKSON:
				try {
					return objMapperRef.get().readValue(content, type);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(SERIALIZE_ERROR, e);
				}
			case GSON:
				return gsonRef.get().fromJson(content, type.getType());
			default:
				throw new AssertionError(SER_TYPE_ERROR + typeRef.get());
		}
	}

	public static <T> T fromJson(String content, Class<T> type) {
		// Initialize the JSON serializers if needed
		init();
		switch (typeRef.get()) {
			case JACKSON:
				try {
					return objMapperRef.get().readValue(content, type);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(SERIALIZE_ERROR, e);
				}
			case GSON:
				return gsonRef.get().fromJson(content, type);
			default:
				throw new AssertionError(SER_TYPE_ERROR + typeRef.get());
		}
	}
}