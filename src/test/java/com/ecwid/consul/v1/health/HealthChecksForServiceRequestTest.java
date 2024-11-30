package com.ecwid.consul.v1.health;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class HealthChecksForServiceRequestTest {
	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.forClass(HealthChecksForServiceRequest.class).verify();
		}
	}
}
