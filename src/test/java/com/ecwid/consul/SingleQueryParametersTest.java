package com.ecwid.consul;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class SingleQueryParametersTest {

	@Test
	public void testToUrlParameters() throws Exception {
		QueryParameters parameters = new SingleQueryParameters("key");
		assertEquals(Collections.singletonList("key"), parameters.getQueryParameters());

		parameters = new SingleQueryParameters("key", "value");
		assertEquals(Collections.singletonList("key=value"), parameters.getQueryParameters());

		parameters = new SingleQueryParameters("key", "value value");
		assertEquals(Collections.singletonList("key=value+value"), parameters.getQueryParameters());
	}

	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.forClass(SingleQueryParameters.class).verify();
		}
	}
}
