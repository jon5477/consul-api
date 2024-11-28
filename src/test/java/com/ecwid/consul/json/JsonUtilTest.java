package com.ecwid.consul.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ecwid.consul.v1.acl.model.AclNodeIdentity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

class JsonUtilTest {
	@Test
	void testToBytesObject() {
		// check the payload is correct
		AclNodeIdentity ni = new AclNodeIdentity("local-node", "local-dc");
		assertEquals("{\"NodeName\":\"local-node\",\"Datacenter\":\"local-dc\"}",
				new String(JsonUtil.toBytes(ni), StandardCharsets.UTF_8));
		// should not include null fields
		ni = new AclNodeIdentity("local-node", null);
		assertEquals("{\"NodeName\":\"local-node\"}", new String(JsonUtil.toBytes(ni), StandardCharsets.UTF_8));
	}

	@Test
	void testToBytesJsonNode() {
		ObjectNode node = JsonNodeFactory.instance.objectNode();
		node.put("type", "unit-test");
		node.put("testing", true);
		assertEquals("{\"type\":\"unit-test\",\"testing\":true}",
				new String(JsonUtil.toBytes(node), StandardCharsets.UTF_8));
	}

	@Test
	void testToJsonNodeByteArray() throws IOException {
		byte[] content = new byte[] { 123, 34, 116, 121, 112, 101, 34, 58, 34, 117, 110, 105, 116, 45, 116, 101, 115,
				116, 34, 44, 34, 116, 101, 115, 116, 105, 110, 103, 34, 58, 116, 114, 117, 101, 125 };
		ObjectNode node = JsonNodeFactory.instance.objectNode();
		node.put("type", "unit-test");
		node.put("testing", true);
		assertEquals(node, JsonUtil.toJsonNode(content));
	}

	@Test
	void testToJsonNodeInputStream() throws IOException {
		byte[] content = new byte[] { 123, 34, 116, 121, 112, 101, 34, 58, 34, 117, 110, 105, 116, 45, 116, 101, 115,
				116, 34, 44, 34, 116, 101, 115, 116, 105, 110, 103, 34, 58, 116, 114, 117, 101, 125 };
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		ObjectNode node = JsonNodeFactory.instance.objectNode();
		node.put("type", "unit-test");
		node.put("testing", true);
		assertEquals(node, JsonUtil.toJsonNode(bais));
	}

	@Test
	void testToPOJOJsonNodeTypeReferenceOfT() {
		ArrayNode node = JsonNodeFactory.instance.arrayNode();
		node.add("test1");
		node.add("test2");
		node.add("test3");
		List<String> list = JsonUtil.toPOJO(node, new TypeReference<List<String>>() {
		});
		assertEquals("test1", list.get(0));
		assertEquals("test2", list.get(1));
		assertEquals("test3", list.get(2));
	}

	@Test
	void testToPOJOJsonNodeClassOfT() throws IOException {
		BooleanNode boolNode = JsonNodeFactory.instance.booleanNode(true);
		assertTrue(JsonUtil.toPOJO(boolNode, boolean.class));
		JsonNode node = JsonUtil.toJsonNode(
				"{\"NodeName\":\"local-node\",\"Datacenter\":\"local-dc\"}".getBytes(StandardCharsets.UTF_8));
		assertEquals(new AclNodeIdentity("local-node", "local-dc"), JsonUtil.toPOJO(node, AclNodeIdentity.class));
	}
}