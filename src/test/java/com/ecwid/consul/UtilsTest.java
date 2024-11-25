package com.ecwid.consul;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilsTest {
	@Test
	public void testToSecondsString() throws Exception {
		assertEquals("1000s", Utils.toSecondsString(1000L));
	}

	@Test
	public void testAssembleAgentAddressWithPath() {
		// Given
		String expectedHost = "http://host";
		int expectedPort = 8888;
		String expectedPath = "path";

		// When
		String actualAddress = Utils.assembleAgentAddress(expectedHost, expectedPort, expectedPath);

		// Then
		assertEquals(String.format("%s:%d/%s", expectedHost, expectedPort, expectedPath), actualAddress);
	}

	@Test
	public void testAssembleAgentAddressWithEmptyPath() {
		// Given
		String expectedHost = "http://host";
		int expectedPort = 8888;
		String expectedPath = "   ";

		// When
		String actualAddress = Utils.assembleAgentAddress(expectedHost, expectedPort, expectedPath);

		// Then
		assertEquals(String.format("%s:%d", expectedHost, expectedPort), actualAddress);
	}

	@Test
	public void testAssembleAgentAddressWithoutPath() {
		// Given
		String expectedHost = "https://host";
		int expectedPort = 8888;

		// When
		String actualAddress = Utils.assembleAgentAddress(expectedHost, expectedPort, null);

		// Then
		assertEquals(String.format("%s:%d", expectedHost, expectedPort), actualAddress);
	}
}
