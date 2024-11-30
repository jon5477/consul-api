package com.ecwid.consul.v1.health.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class HealthServiceTest {
	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.simple().forClass(HealthService.class).verify();
		}
	}
}