package com.ecwid.consul.v1.catalog;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class CatalogServicesRequestTest {
	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.forClass(CatalogServicesRequest.class).verify();
		}
	}
}
