package com.ecwid.consul;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class SingleQueryParametersTest {
	private static final String KEY = "key";

	@Test
	void testEmptyQueryParameters() {
		QueryParameters parameters = new SingleQueryParameters(KEY);
		Map<String, List<CharSequence>> queryParams = parameters.getQueryParameters();
		assertEquals(Collections.singletonMap(KEY, new ArrayList<>()), queryParams);
		assertEquals(Collections.emptyList(), queryParams.get(KEY));
	}

	@Test
	void testSingleValueQueryParameters() {
		QueryParameters parameters = new SingleQueryParameters(KEY, "value");
		Map<String, List<CharSequence>> queryParams = parameters.getQueryParameters();
		assertEquals(Collections.singletonMap(KEY, Utils.asList("value")), queryParams);
		assertInstanceOf(ArrayList.class, queryParams.get(KEY));
	}

	@Test
	void testMultiValueQueryParameters() {
		QueryParameters parameters = new SingleQueryParameters(KEY, "value value");
		Map<String, List<CharSequence>> queryParams = parameters.getQueryParameters();
		assertEquals(Collections.singletonMap(KEY, Utils.asList("value value")), queryParams);
		assertInstanceOf(ArrayList.class, queryParams.get(KEY));
	}

	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.forClass(SingleQueryParameters.class).verify();
		}
	}
}
