package com.ecwid.consul;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UtilsTest {
	@Test
	void testToSecondsString() {
		assertEquals("1000s", Utils.toSecondsString(1000L));
	}

	@Test
	void testAssembleAgentAddressWithPath() {
		// Given
		String expectedHost = "http://host";
		int expectedPort = 8888;
		String expectedPath = "path";

		// When
		String actualAddress = Utils.assembleAgentAddress(expectedHost, expectedPort, expectedPath);

		// Then
		assertEquals(expectedHost + ":" + expectedPort + "/" + expectedPath, actualAddress);
	}

	@Test
	void testAssembleAgentAddressWithEmptyPath() {
		// Given
		String expectedHost = "http://host";
		int expectedPort = 8888;
		String expectedPath = "   ";

		// When
		String actualAddress = Utils.assembleAgentAddress(expectedHost, expectedPort, expectedPath);

		// Then
		assertEquals(expectedHost + ":" + expectedPort, actualAddress);
	}

	@Test
	void testAssembleAgentAddressWithoutPath() {
		// Given
		String expectedHost = "https://host";
		int expectedPort = 8888;

		// When
		String actualAddress = Utils.assembleAgentAddress(expectedHost, expectedPort, null);

		// Then
		assertEquals(expectedHost + ":" + expectedPort, actualAddress);
	}
}
