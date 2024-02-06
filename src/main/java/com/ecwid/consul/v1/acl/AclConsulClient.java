package com.ecwid.consul.v1.acl;

import java.util.List;

import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.transport.TLSConfig;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.Request;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.acl.model.AclToken;
import com.ecwid.consul.v1.acl.model.NewAcl;
import com.ecwid.consul.v1.acl.model.UpdateAcl;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Jon Huang (jon5477)
 */
public final class AclConsulClient implements AclClient {
	private static final String API_TOKEN_PREFIX = "/v1/acl/token/";
	private final ConsulRawClient rawClient;

	public AclConsulClient(ConsulRawClient rawClient) {
		this.rawClient = rawClient;
	}

	public AclConsulClient() {
		this(new ConsulRawClient());
	}

	public AclConsulClient(TLSConfig tlsConfig) {
		this(new ConsulRawClient(tlsConfig));
	}

	public AclConsulClient(String agentHost) {
		this(new ConsulRawClient(agentHost));
	}

	public AclConsulClient(String agentHost, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, tlsConfig));
	}

	public AclConsulClient(String agentHost, int agentPort) {
		this(new ConsulRawClient(agentHost, agentPort));
	}

	public AclConsulClient(String agentHost, int agentPort, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, agentPort, tlsConfig));
	}

	@Override
	public Response<AclToken> aclCreate(String token, NewAcl newAcl) {
		String json = JsonFactory.toJson(newAcl);
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/acl/token").setContent(json).setToken(token)
				.build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.fromJson(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<AclToken> aclRead(String token, String accessorId) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_TOKEN_PREFIX + accessorId).setToken(token)
				.build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.fromJson(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<AclToken> aclReadSelf(String token) {
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/acl/token/self").setToken(token).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.fromJson(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<AclToken> aclUpdate(String token, UpdateAcl updateAcl, String accessorId) {
		String json = JsonFactory.toJson(updateAcl);
		Request request = Request.Builder.newBuilder().setEndpoint(API_TOKEN_PREFIX + accessorId).setContent(json)
				.setToken(token).build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.fromJson(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<AclToken> aclClone(String token, String accessorId) {
		Request request = Request.Builder.newBuilder().setEndpoint(API_TOKEN_PREFIX + accessorId + "/clone")
				.setContent("{}").setToken(token).build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			AclToken aclToken = JsonFactory.fromJson(httpResponse.getContent(), AclToken.class);
			return new Response<>(aclToken, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> aclDelete(String token, String accessorId) {
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
	public Response<List<AclToken>> aclList(String token, AclTokensRequest req) {
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/acl/tokens")
				.addUrlParameters(req.asUrlParameters()).setToken(token).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<AclToken> aclTokens = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<AclToken>>() {
					});
			return new Response<>(aclTokens, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}