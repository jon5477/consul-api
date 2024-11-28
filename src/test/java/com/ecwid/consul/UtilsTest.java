package com.ecwid.consul;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.CharBuffer;
import java.time.Duration;

import org.junit.jupiter.api.Test;

class UtilsTest {
	@Test
	void testCharSequenceToArray() {
		char[] expected = new char[] { 't', 'e', 's', 't' };
		CharBuffer cbuf = CharBuffer.wrap(expected);
		StringBuilder sb = new StringBuilder().append(expected);
		assertArrayEquals(expected, Utils.charSequenceToArray(cbuf));
		assertArrayEquals(expected, Utils.charSequenceToArray(sb));
		assertArrayEquals(expected, Utils.charSequenceToArray("test"));
	}

	@Test
	void testCharSequenceEquals() {
		char[] expected = new char[] { 't', 'e', 's', 't' };
		CharBuffer cbuf = CharBuffer.wrap(expected);
		StringBuilder sb = new StringBuilder().append(expected);
		assertTrue(Utils.charSequenceEquals(null, null));
		assertFalse(Utils.charSequenceEquals(cbuf, null));
		assertFalse(Utils.charSequenceEquals(null, cbuf));
		assertTrue(Utils.charSequenceEquals(cbuf, cbuf));
		assertTrue(Utils.charSequenceEquals(cbuf, sb));
		assertTrue(Utils.charSequenceEquals(cbuf, "test"));
		assertFalse(Utils.charSequenceEquals(cbuf, "nope"));
		assertFalse(Utils.charSequenceEquals(cbuf, "testing"));
		assertTrue(Utils.charSequenceEquals("", ""));
		assertTrue(Utils.charSequenceEquals("a", "a"));
		assertFalse(Utils.charSequenceEquals("a", "b"));
	}

	@Test
	void testToConsulDuration() {
		// Null duration
		assertThrows(NullPointerException.class, () -> Utils.toConsulDuration(null));

		// Negative durations
		Duration negDur = Duration.ofSeconds(-10);
		assertThrows(IllegalArgumentException.class, () -> Utils.toConsulDuration(negDur));
		Duration negDur2 = Duration.ofHours(-1).plusMinutes(-30);
		assertThrows(IllegalArgumentException.class, () -> Utils.toConsulDuration(negDur2));

		// Zero duration
		assertEquals("0s", Utils.toConsulDuration(Duration.ZERO), "Zero duration should return '0s'");

		// Positive durations
		assertEquals("30s", Utils.toConsulDuration(Duration.ofSeconds(30)), "30 seconds duration mismatch");
		assertEquals("5m", Utils.toConsulDuration(Duration.ofMinutes(5)), "5 minutes duration mismatch");
		assertEquals("2h", Utils.toConsulDuration(Duration.ofHours(2)), "2 hours duration mismatch");
		assertEquals("1h15m30s", Utils.toConsulDuration(Duration.ofHours(1).plusMinutes(15).plusSeconds(30)),
				"Mixed units duration mismatch");

		// Large durations
		assertEquals("25h", Utils.toConsulDuration(Duration.ofDays(1).plusHours(1)),
				"Large duration (25 hours) mismatch");

		// Milliseconds
		assertEquals("123ms", Utils.toConsulDuration(Duration.ofMillis(123)), "Milliseconds mismatch");

		// Nanoseconds
		assertEquals("500µs", Utils.toConsulDuration(Duration.ofNanos(500000)), "Microseconds mismatch");

		// Combined milliseconds, microseconds, and nanoseconds
		assertEquals("1ms2µs", Utils.toConsulDuration(Duration.ofMillis(1).plusNanos(2000)),
				"Mixed milliseconds and microseconds mismatch");

		// Combined milliseconds and nanoseconds
		assertEquals("1ms337ns", Utils.toConsulDuration(Duration.ofMillis(1).plusNanos(337)),
				"Mixed milliseconds and microseconds mismatch");

		// Combined milliseconds, microseconds, and nanoseconds
		assertEquals("1ms501µs337ns", Utils.toConsulDuration(Duration.ofMillis(1).plusNanos(501337)),
				"Mixed milliseconds, microseconds, and nanoseconds mismatch");
	}

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
