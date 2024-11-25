package com.ecwid.consul.v1.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecwid.consul.ConsulTestConstants;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.catalog.model.Node;
import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.infrastructure.Ports;

class CatalogConsulClientTest {
	private ConsulProcess consul;
	private int port = Ports.nextAvailable();
	private CatalogConsulClient consulClient = new CatalogConsulClient(
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
	void testGetCatalogNodes() {
		CatalogNodesRequest request = CatalogNodesRequest.newBuilder().build();
		Response<List<Node>> response = consulClient.getCatalogNodes(request);

		// We should find only one node – this
		assertEquals(1, response.getValue().size());
	}
}