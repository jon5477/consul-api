package com.ecwid.consul.v1.acl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecwid.consul.ConsulTestConstants;
import com.ecwid.consul.Utils;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.acl.model.AclToken;
import com.ecwid.consul.v1.acl.model.NewAcl;
import com.ecwid.consul.v1.acl.model.UpdateAcl;
import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.infrastructure.Ports;

class AclConsulClientTest {
	private static final char[] ACL_MASTER_TOKEN = "mastertoken".toCharArray();
	private ConsulProcess consul;
	private int port = Ports.nextAvailable();
	private AclClient aclClient = new AclConsulClient("localhost", port);

	@BeforeEach
	public void setup() {
		String customConfiguration = "{\n" + "  \"acl\": {\n" + "    \"enabled\": true,\n"
				+ "    \"enable_token_persistence\": true,\n" + "    \"tokens\": {\n"
				+ "      \"initial_management\": \"" + new String(ACL_MASTER_TOKEN) + "\"\n" + "    }\n" + "  },\n"
				+ "  \"primary_datacenter\": \"dc-test\",\n" + "  \"datacenter\": \"dc-test\"\n" + "}";
		consul = ConsulStarterBuilder.consulStarter().withConsulVersion(ConsulTestConstants.CONSUL_VERSION)
				.withHttpPort(port).withCustomConfig(customConfiguration).build().start();
	}

	@AfterEach
	public void cleanup() throws Exception {
		consul.close();
	}

	private AclToken createTestToken() {
		UUID uuid = UUID.fromString("ae76fb5a-6fd7-b764-d587-06b921280a8a");
		NewAcl newAcl = new NewAcl();
		newAcl.setAccessorId(uuid.toString());
		newAcl.setSecretId("c604a178-e102-af3a-cbd8-8febf4b26468");
		newAcl.setDescription("Test ACL Token");
		Response<AclToken> response = aclClient.aclCreate(ACL_MASTER_TOKEN, newAcl);
		return response.getValue();
	}

	@Test
	void should_create_acl_token() {
		NewAcl newAcl = new NewAcl();
		newAcl.setDescription("test-acl");
		Response<AclToken> response = aclClient.aclCreate(ACL_MASTER_TOKEN, newAcl);
		AclToken createdAcl = response.getValue();
		assertEquals(newAcl.getDescription(), createdAcl.getDescription());
	}

	@Test
	void should_read_acl_token() {
		AclToken token = createTestToken();
		Response<AclToken> response = aclClient.aclRead(ACL_MASTER_TOKEN, token.getAccessorId());
		assertEquals(token, response.getValue());
	}

	@Test
	void should_read_self_acl_token() {
		AclToken token = createTestToken();
		char[] secretId = Utils.charSequenceToArray(token.getSecretId());
		Response<AclToken> response = aclClient.aclReadSelf(secretId);
		assertEquals(token, response.getValue());

	}

	@Test
	void should_update_acl_token() {
		AclToken token = createTestToken();
		UpdateAcl update = new UpdateAcl();
		update.setDescription("Test Updating ACL Token Description");
		Response<AclToken> response = aclClient.aclUpdate(ACL_MASTER_TOKEN, update, token.getAccessorId());
		assertEquals(update.getDescription(), response.getValue().getDescription());
	}

	@Test
	void should_clone_acl_token() {
		AclToken token = createTestToken();
		Response<AclToken> response = aclClient.aclClone(ACL_MASTER_TOKEN, token.getAccessorId());
		AclToken cloned = response.getValue();
		assertNotEquals(token, cloned);
		assertNotEquals(token.getAccessorId(), cloned.getAccessorId());
		assertNotEquals(token.getSecretId(), cloned.getSecretId());
	}

	@Test
	void should_delete_acl_token() {
		AclToken token = createTestToken();
		aclClient.aclDelete(ACL_MASTER_TOKEN, token.getAccessorId());
		assertThrows(OperationException.class, () -> aclClient.aclRead(ACL_MASTER_TOKEN, token.getAccessorId()));
	}

	@Test
	void should_list_acl_tokens() {
		Response<List<AclToken>> response = aclClient.aclList(ACL_MASTER_TOKEN, AclTokensRequest.newBuilder().build());
		List<AclToken> tokens = response.getValue();
		assertFalse(tokens.isEmpty());
	}
}