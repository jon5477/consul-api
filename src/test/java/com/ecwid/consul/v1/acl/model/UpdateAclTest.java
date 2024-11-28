package com.ecwid.consul.v1.acl.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class UpdateAclTest {
	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.simple().forClass(UpdateAcl.class).withPrefabValues(CharSequence.class, "red", "blue")
					.verify();
		}
	}
}