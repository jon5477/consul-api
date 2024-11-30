package com.ecwid.consul.v1.health;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.ecwid.consul.v1.Filter;
import com.ecwid.consul.v1.Filter.MatchingOperator;

import nl.jqno.equalsverifier.EqualsVerifier;

class HealthServicesRequestTest {
	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			Filter f1 = Filter.of(MatchingOperator.EQUAL, Filter.Selector.of("Service.Tags"), "UnitTest");
			Filter f2 = Filter.of(MatchingOperator.EQUAL, Filter.Selector.of("Node.Meta"), "TestNode");
			EqualsVerifier.forClass(HealthServicesRequest.class).withPrefabValues(Filter.class, f1, f2).verify();
		}
	}
}