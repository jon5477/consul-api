package com.ecwid.consul.v1.acl.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class AclTokenTest {
	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.simple().forClass(AclToken.class).withPrefabValues(CharSequence.class, "red", "blue")
					.verify();
		}
	}
}