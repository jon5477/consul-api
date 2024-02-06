package com.ecwid.consul.v1.acl;

import java.util.List;
import java.util.Map;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.SingleUrlParameters;
import com.ecwid.consul.UrlParameters;
import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.transport.TLSConfig;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.Request;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.acl.model.legacy.LegacyAcl;
import com.ecwid.consul.v1.acl.model.legacy.LegacyNewAcl;
import com.ecwid.consul.v1.acl.model.legacy.LegacyUpdateAcl;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class LegacyAclConsulClient implements LegacyAclClient {
	private final ConsulRawClient rawClient;

	public LegacyAclConsulClient(ConsulRawClient rawClient) {
		this.rawClient = rawClient;
	}

	public LegacyAclConsulClient() {
		this(new ConsulRawClient());
	}

	public LegacyAclConsulClient(TLSConfig tlsConfig) {
		this(new ConsulRawClient(tlsConfig));
	}

	public LegacyAclConsulClient(String agentHost) {
		this(new ConsulRawClient(agentHost));
	}

	public LegacyAclConsulClient(String agentHost, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, tlsConfig));
	}

	public LegacyAclConsulClient(String agentHost, int agentPort) {
		this(new ConsulRawClient(agentHost, agentPort));
	}

	public LegacyAclConsulClient(String agentHost, int agentPort, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, agentPort, tlsConfig));
	}

	@Override
	public Response<String> aclCreate(LegacyNewAcl newAcl, String token) {
		String json = JsonFactory.toJson(newAcl);
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/acl/create").setContent(json).setToken(token)
				.build();
		HttpResponse httpResponse = rawClient.makePutRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			Map<String, String> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<Map<String, String>>() {
					});
			return new Response<>(value.get("ID"), httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> aclUpdate(LegacyUpdateAcl updateAcl, String token) {
		UrlParameters tokenParams = token != null ? new SingleUrlParameters("token", token) : null;
		String json = JsonFactory.toJson(updateAcl);
		HttpResponse httpResponse = rawClient.makePutRequest("/v1/acl/update", json, tokenParams);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> aclDestroy(String aclId, String token) {
		UrlParameters tokenParams = token != null ? new SingleUrlParameters("token", token) : null;
		HttpResponse httpResponse = rawClient.makePutRequest("/v1/acl/destroy/" + aclId, "", tokenParams);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<LegacyAcl> getAcl(String id) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/acl/info/" + id);
		if (httpResponse.getStatusCode() == 200) {
			List<LegacyAcl> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<LegacyAcl>>() {
					});
			if (value.isEmpty()) {
				return new Response<>(null, httpResponse);
			} else if (value.size() == 1) {
				return new Response<>(value.get(0), httpResponse);
			} else {
				throw new ConsulException("Strange response (list size=" + value.size() + ")");
			}
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<String> aclClone(String aclId, String token) {
		UrlParameters tokenParams = token != null ? new SingleUrlParameters("token", token) : null;
		HttpResponse httpResponse = rawClient.makePutRequest("/v1/acl/clone/" + aclId, "", tokenParams);
		if (httpResponse.getStatusCode() == 200) {
			Map<String, String> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<Map<String, String>>() {
					});
			return new Response<>(value.get("ID"), httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<LegacyAcl>> getAclList(String token) {
		UrlParameters tokenParams = token != null ? new SingleUrlParameters("token", token) : null;
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/acl/list", tokenParams);
		if (httpResponse.getStatusCode() == 200) {
			List<LegacyAcl> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<LegacyAcl>>() {
					});
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}