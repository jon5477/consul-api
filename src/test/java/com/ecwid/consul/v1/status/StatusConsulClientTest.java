package com.ecwid.consul.v1.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecwid.consul.ConsulTestConstants;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.Response;
import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.infrastructure.Ports;

class StatusConsulClientTest {
	private ConsulProcess consul;
	private int port = Ports.nextAvailable();
	private StatusConsulClient consulClient = new StatusConsulClient(
			new ConsulRawClient.Builder("localhost", port).build());

	@BeforeEach
	void setUp() {
		this.consul = ConsulStarterBuilder.consulStarter().withConsulVersion(ConsulTestConstants.CONSUL_VERSION)
				.withHttpPort(port).build().start();
	}

	@AfterEach
	void tearDown() {
		this.consul.close();
	}

	@Test
	void testGetStatusLeader() {
		String serverAddress = consul.getAddress();
		int serverPort = consul.getServerPort();
		Response<String> response = consulClient.getStatusLeader();
		String statusLeader = response.getValue();
		assertEquals(serverAddress + ":" + serverPort, statusLeader);
	}

	@Test
	void testGetStatusPeers() {
		String serverAddress = consul.getAddress();
		int serverPort = consul.getServerPort();
		Response<List<String>> response = consulClient.getStatusPeers();
		List<String> statusPeers = response.getValue();
		assertEquals(1, statusPeers.size());
		assertEquals(serverAddress + ":" + serverPort, statusPeers.get(0));
	}
}