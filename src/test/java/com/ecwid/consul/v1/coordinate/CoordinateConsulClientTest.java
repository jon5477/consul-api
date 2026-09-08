package com.ecwid.consul.v1.coordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ecwid.consul.json.JsonUtil;
import com.ecwid.consul.transport.ConsulHttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.coordinate.model.Coord;
import com.ecwid.consul.v1.coordinate.model.Datacenter;
import com.ecwid.consul.v1.coordinate.model.Node;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

class CoordinateConsulClientTest {
	private static final String DATACENTER = "dc1";
	private static final String TEST_SERVER_NODE_NAME = "test-server-node";
	private static final QueryParams DC_QUERY_PARAMS = new QueryParams.Builder().setDatacenter(DATACENTER).build();
	private ConsulRawClient mockClient;
	private CoordinateConsulClient consulClient;

	@BeforeEach
	void setUp() {
		this.mockClient = Mockito.mock(ConsulRawClient.class);
		this.consulClient = new CoordinateConsulClient(mockClient);
	}

	@AfterEach
	void tearDown() {
		this.mockClient = null;
		this.consulClient = null;
	}

	private JsonNode getDatacentersResponse() {
		ArrayNode arrNode = JsonNodeFactory.instance.arrayNode();
		ObjectNode dcEntry = arrNode.insertObject(0);
		dcEntry.put("Datacenter", DATACENTER);
		dcEntry.put("AreaID", "wan");
		ArrayNode coordsArr = dcEntry.putArray("Coordinates");
		ObjectNode nodeEntry = coordsArr.insertObject(0);
		nodeEntry.put("Node", TEST_SERVER_NODE_NAME);
		nodeEntry.put("Segment", "");
		ObjectNode coord = nodeEntry.putObject("Coord");
		ArrayNode vec = coord.putArray("Vec");
		for (int i = 0; i < 8; i++) {
			vec.add(0);
		}
		coord.put("Error", 1.5);
		coord.put("Adjustment", 0);
		coord.put("Height", 0.00001);
		return arrNode;
	}

	@Test
	void testGetDatacenters() {
		Mockito.when(mockClient.makeGetRequest("/v1/coordinate/datacenters"))
				.thenReturn(new ConsulHttpResponse(200, getDatacentersResponse(), null, true, 0L, 1L));
		Response<List<Datacenter>> response = consulClient.getDatacenters();
		assertEquals(1, response.getConsulIndex());
		assertTrue(response.isConsulKnownLeader());
		assertEquals(0, response.getConsulLastContact());
		List<Datacenter> datacenters = response.getValue();
		assertEquals(1, datacenters.size());
		Datacenter dc = datacenters.get(0);
		assertEquals(DATACENTER, dc.getDatacenter());
		assertEquals("wan", dc.getAreaId());
		assertEquals(1, dc.getCoordinates().size());
		Node n = dc.getCoordinates().get(0);
		assertEquals(TEST_SERVER_NODE_NAME, n.getNode());
		assertEquals("", n.getSegment());
		Coord c = n.getCoord();
		assertEquals(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), c.getVec());
		assertEquals(1.5, c.getError());
		assertEquals(0.0, c.getAdjustment());
		assertEquals(0.00001, c.getHeight());
	}

	private ArrayNode getNodesResponse() {
		ArrayNode arrNode = JsonNodeFactory.instance.arrayNode();
		ObjectNode nodeEntry = arrNode.insertObject(0);
		nodeEntry.put("Node", TEST_SERVER_NODE_NAME);
		nodeEntry.put("Segment", "");
		ObjectNode coord = nodeEntry.putObject("Coord");
		ArrayNode vec = coord.putArray("Vec");
		for (int i = 0; i < 8; i++) {
			vec.add(0);
		}
		coord.put("Error", 1.5);
		coord.put("Adjustment", 0);
		coord.put("Height", 0.00001);
		return arrNode;
	}

	@Test
	void testGetNodes() {
		Mockito.when(mockClient.makeGetRequest("/v1/coordinate/nodes", DC_QUERY_PARAMS))
				.thenReturn(new ConsulHttpResponse(200, getNodesResponse(), null, true, 0L, 1L));
		Response<List<Node>> response = consulClient.getNodes(DC_QUERY_PARAMS);
		assertEquals(1, response.getConsulIndex());
		assertTrue(response.isConsulKnownLeader());
		assertEquals(0, response.getConsulLastContact());
		List<Node> nodes = response.getValue();
		assertEquals(1, nodes.size());
		Node n = nodes.get(0);
		assertEquals(TEST_SERVER_NODE_NAME, n.getNode());
		assertEquals("", n.getSegment());
		Coord c = n.getCoord();
		assertEquals(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), c.getVec());
		assertEquals(1.5, c.getError());
		assertEquals(0.0, c.getAdjustment());
		assertEquals(0.00001, c.getHeight());
	}

	@Test
	void testGetNode() {
		Mockito.when(mockClient.makeGetRequest("/v1/coordinate/node/" + TEST_SERVER_NODE_NAME, DC_QUERY_PARAMS))
				.thenReturn(new ConsulHttpResponse(200, getNodesResponse(), null, true, 0L, 1L));
		Response<List<Node>> response = consulClient.getNode(TEST_SERVER_NODE_NAME, DC_QUERY_PARAMS);
		assertEquals(1, response.getConsulIndex());
		assertTrue(response.isConsulKnownLeader());
		assertEquals(0, response.getConsulLastContact());
		List<Node> nodes = response.getValue();
		assertEquals(1, nodes.size());
		Node n = nodes.get(0);
		assertEquals(TEST_SERVER_NODE_NAME, n.getNode());
		assertEquals("", n.getSegment());
		Coord c = n.getCoord();
		assertEquals(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), c.getVec());
		assertEquals(1.5, c.getError());
		assertEquals(0.0, c.getAdjustment());
		assertEquals(0.00001, c.getHeight());
	}

	@Test
	void testUpdateNode() {
		Mockito.when(mockClient.makePutRequest(Mockito.eq("/v1/coordinate/update"), Mockito.any(byte[].class),
				Mockito.eq(DC_QUERY_PARAMS))).thenReturn(new ConsulHttpResponse(200, null, null, true, 0L, 1L));
		Node node = new Node();
		node.setNode("agent-one");
		Coord coord = new Coord();
		coord.setAdjustment(0.0);
		coord.setError(1.5);
		coord.setHeight(0.0);
		coord.setVec(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
		node.setCoord(coord);
		byte[] json = JsonUtil.toBytes(node);
		consulClient.updateNode(node, DC_QUERY_PARAMS);
		Mockito.verify(mockClient, Mockito.times(1)).makePutRequest(Mockito.eq("/v1/coordinate/update"),
				Mockito.eq(json), Mockito.eq(DC_QUERY_PARAMS));
	}
}