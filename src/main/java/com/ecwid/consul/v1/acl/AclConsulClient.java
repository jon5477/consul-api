package com.ecwid.consul.v1.acl;

import java.util.List;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.Request;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.acl.model.AclToken;
import com.ecwid.consul.v1.acl.model.NewAcl;
import com.ecwid.consul.v1.acl.model.UpdateAcl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Jon Huang (jon5477)
 */
public final class AclConsulClient implements AclClient {
	private static final TypeReference<List<AclToken>> TOKEN_LIST_TYPE_REF = new TypeReference<List<AclToken>>() {
	};
	private static final String API_TOKEN_PREFIX = "/v1/acl/token/";
	private final ConsulRawClient rawClient;

	public AclConsulClient(@NonNull ConsulRawClient rawClient) {
		this.rawClient = Objects.requireNonNull(rawClient, "consul raw client cannot be null");
	}

	@Override
	public Response<AclToken> aclCreate(char[] token, NewAcl newAcl) {
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/acl/token")
				.setContent(JsonFactory.toBytes(newAcl)).setToken(token).build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.toPOJO(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<AclToken> aclRead(char[] token, String accessorId) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_TOKEN_PREFIX + accessorId).setToken(token)
				.build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.toPOJO(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<AclToken> aclReadSelf(char[] token) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_TOKEN_PREFIX + "self").setToken(token).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.toPOJO(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<AclToken> aclUpdate(char[] token, UpdateAcl updateAcl, String accessorId) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_TOKEN_PREFIX + accessorId)
				.setContent(JsonFactory.toBytes(updateAcl)).setToken(token).build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.toPOJO(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<AclToken> aclClone(char[] token, @NonNull String accessorId) {
		return aclClone(token, accessorId, null);
	}

	@Override
	public Response<AclToken> aclClone(char[] token, @NonNull String accessorId, @Nullable String description) {
		ObjectNode objNode = JsonNodeFactory.instance.objectNode();
		if (description != null) {
			objNode.put("Description", description);
		}
		Request request = Request.Builder.newBuilder().setEndpoint(API_TOKEN_PREFIX + accessorId + "/clone")
				.setContent(JsonFactory.toBytes(objNode)).setToken(token).build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.toPOJO(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> aclDelete(char[] token, String accessorId) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_TOKEN_PREFIX + accessorId).setToken(token)
				.build();
		HttpResponse httpResponse = rawClient.makeDeleteRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<AclToken>> aclList(char[] token, AclTokensRequest req) {
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/acl/tokens")
				.addQueryParameters(req.getQueryParameters()).setToken(token).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<AclToken> aclTokens = JsonFactory.toPOJO(httpResponse.getContent(), TOKEN_LIST_TYPE_REF);
			return new Response<>(aclTokens, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}