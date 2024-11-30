package com.ecwid.consul;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class SingleQueryParametersTest {
	@Test
	void testToUrlParameters() {
		QueryParameters parameters = new SingleQueryParameters("key");
		assertEquals(Collections.singletonMap("key", null), parameters.getQueryParameters());
		parameters = new SingleQueryParameters("key", "value");
		assertEquals(Collections.singletonMap("key", "value"), parameters.getQueryParameters());
		parameters = new SingleQueryParameters("key", "value value");
		assertEquals(Collections.singletonMap("key", "value value"), parameters.getQueryParameters());
	}

	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.forClass(SingleQueryParameters.class).verify();
		}
	}
}
