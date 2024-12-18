package com.ecwid.consul.v1.kv;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecwid.consul.ConsulTestConstants;
import com.ecwid.consul.v1.ConsulRawClient;
import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.infrastructure.Ports;

class KeyValueConsulClientTest {
	private static final Random rnd = new Random();
	private ConsulProcess consul;
	private int port = Ports.nextAvailable();
	private KeyValueConsulClient consulClient = new KeyValueConsulClient(
			new ConsulRawClient.Builder("localhost", port).build());

	@BeforeEach
	void setUp() {
		consul = ConsulStarterBuilder.consulStarter().withConsulVersion(ConsulTestConstants.CONSUL_VERSION)
				.withHttpPort(port).build().start();
	}

	@AfterEach
	void tearDown() {
		consul.close();
	}

	@Test
	void testSetKVValue() {
		String testKey = "test_kv";
		String testValue = "test_value";
		// Make sure there is no such key before test running
		assertNull(consulClient.getKVValue(testKey).getValue());
		// Set the key
		consulClient.setKVValue(testKey, testValue);
		// Make sure test key exists
		assertEquals(testValue, consulClient.getKVValue(testKey).getValue().getDecodedValue());
	}

	@Test
	void testSetKVBinaryValue() {
		String testKey = "test_binary";
		byte[] testValue = new byte[100];
		rnd.nextBytes(testValue);
		// Make sure there is no such key before test running
		assertNull(consulClient.getKVValue(testKey).getValue());
		// Set the key
		consulClient.setKVBinaryValue(testKey, testValue);
		// Make sure test key exists
		assertArrayEquals(testValue, consulClient.getKVBinaryValue(testKey).getValue().getValue());
	}

	@Test
	void testDeleteKvValue() {
		String testKey = "test_key";
		String testValue = "test_value";
		// Make sure there is no such key before test running
		assertNull(consulClient.getKVValue(testKey).getValue());
		// Set the key
		consulClient.setKVValue(testKey, testValue);
		// Make sure test key exists
		assertEquals(testValue, consulClient.getKVValue(testKey).getValue().getDecodedValue());
		// Delete key
		assertTrue(consulClient.deleteKVValue(testKey).getValue());
		// Make sure there is no such key before test running
		assertNull(consulClient.getKVValue(testKey).getValue());
	}

	@Test
	void testDeleteKvValues() {
		String testKeyPrefix = "test_key";
		String testValue = "test_value";
		// Prepare test keys under testKeyPrefix prefix
		for (int i = 0; i < 10; i++) {
			String testKey = testKeyPrefix + "/" + i;
			// Make sure there is no such key before test running
			assertNull(consulClient.getKVValue(testKey).getValue());
			// Set the key
			consulClient.setKVValue(testKey, testValue);
			// Make sure test key exists
			assertEquals(testValue, consulClient.getKVValue(testKey).getValue().getDecodedValue());
		}
		// Delete all keys in single shot
		assertTrue(consulClient.deleteKVValues(testKeyPrefix).getValue());
		// Make sure all keys have been deleted
		assertNull(consulClient.getKVKeysOnly(testKeyPrefix).getValue());
	}
}