package com.ecwid.consul.v1.session;

import java.util.List;
import java.util.Map;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.session.model.NewSession;
import com.ecwid.consul.v1.session.model.Session;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class SessionConsulClient implements SessionClient {
	private static final TypeReference<List<Session>> SESSION_LIST_TYPE_REF = new TypeReference<List<Session>>() {
	};
	private static final TypeReference<Map<String, String>> STRING_MAP_TYPE_REF = new TypeReference<Map<String, String>>() {
	};
	private final ConsulRawClient rawClient;

	public SessionConsulClient(ConsulRawClient rawClient) {
		this.rawClient = rawClient;
	}

	public SessionConsulClient() {
		this(new ConsulRawClient());
	}

	public SessionConsulClient(String agentHost) {
		this(new ConsulRawClient(agentHost));
	}

	public SessionConsulClient(String agentHost, int agentPort) {
		this(new ConsulRawClient(agentHost, agentPort));
	}

	@Override
	public Response<String> sessionCreate(NewSession newSession, QueryParams queryParams) {
		return sessionCreate(newSession, queryParams, null);
	}

	@Override
	public Response<String> sessionCreate(NewSession newSession, QueryParams queryParams, String token) {
		String json = JsonFactory.toJson(newSession);
		HttpResponse httpResponse = rawClient.makePutRequest("/v1/session/create", json, queryParams, tokenParam);
		if (httpResponse.getStatusCode() == 200) {
			Map<String, String> value = JsonFactory.toPOJO(httpResponse.getContent(), STRING_MAP_TYPE_REF);
			return new Response<>(value.get("ID"), httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> sessionDestroy(String session, QueryParams queryParams) {
		return sessionDestroy(session, queryParams, null);
	}

	@Override
	public Response<Void> sessionDestroy(String session, QueryParams queryParams, String token) {
		HttpResponse httpResponse = rawClient.makePutRequest("/v1/session/destroy/" + session, "", queryParams,
				tokenParam);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Session> getSessionInfo(String session, QueryParams queryParams) {
		return getSessionInfo(session, queryParams, null);
	}

	@Override
	public Response<Session> getSessionInfo(String session, QueryParams queryParams, String token) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/session/info/" + session, queryParams, tokenParam);
		if (httpResponse.getStatusCode() == 200) {
			List<Session> value = JsonFactory.toPOJO(httpResponse.getContent(), SESSION_LIST_TYPE_REF);
			if (value == null || value.isEmpty()) {
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
	public Response<List<Session>> getSessionNode(String node, QueryParams queryParams) {
		return getSessionNode(node, queryParams, null);
	}

	@Override
	public Response<List<Session>> getSessionNode(String node, QueryParams queryParams, String token) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/session/node/" + node, queryParams, tokenParam);
		if (httpResponse.getStatusCode() == 200) {
			List<Session> value = JsonFactory.toPOJO(httpResponse.getContent(), SESSION_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Session>> getSessionList(QueryParams queryParams) {
		return getSessionList(queryParams, null);
	}

	@Override
	public Response<List<Session>> getSessionList(QueryParams queryParams, String token) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/session/list", queryParams, tokenParam);
		if (httpResponse.getStatusCode() == 200) {
			List<Session> value = JsonFactory.toPOJO(httpResponse.getContent(), SESSION_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	public Response<Session> renewSession(String session, QueryParams queryParams) {
		return renewSession(session, queryParams, null);
	}

	@Override
	public Response<Session> renewSession(String session, QueryParams queryParams, String token) {
		HttpResponse httpResponse = rawClient.makePutRequest("/v1/session/renew/" + session, "", queryParams,
				tokenParam);
		if (httpResponse.getStatusCode() == 200) {
			List<Session> value = JsonFactory.toPOJO(httpResponse.getContent(), SESSION_LIST_TYPE_REF);
			if (value.size() == 1) {
				return new Response<>(value.get(0), httpResponse);
			} else {
				throw new ConsulException("Strange response (list size=" + value.size() + ")");
			}
		} else {
			throw new OperationException(httpResponse);
		}
	}
}