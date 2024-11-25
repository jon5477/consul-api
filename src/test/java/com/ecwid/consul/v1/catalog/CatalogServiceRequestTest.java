package com.ecwid.consul.v1.catalog;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class CatalogServiceRequestTest {
	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.forClass(CatalogServiceRequest.class).verify();
		}
	}
}
