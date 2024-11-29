package com.ecwid.consul.v1.status;

import java.util.List;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.ecwid.consul.json.JsonUtil;
import com.ecwid.consul.transport.ConsulHttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.Response;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class StatusConsulClient implements StatusClient {
	private static final TypeReference<List<String>> STRING_LIST_TYPE_REF = new TypeReference<List<String>>() {
	};
	private final ConsulRawClient rawClient;

	public StatusConsulClient(@NonNull ConsulRawClient rawClient) {
		this.rawClient = Objects.requireNonNull(rawClient, "consul raw client cannot be null");
	}

	@Override
	public Response<String> getStatusLeader() {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/status/leader");
		if (httpResponse.getStatusCode() == 200) {
			String value = JsonUtil.toPOJO(httpResponse.getContent(), String.class);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<String>> getStatusPeers() {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/status/peers");
		if (httpResponse.getStatusCode() == 200) {
			List<String> value = JsonUtil.toPOJO(httpResponse.getContent(), STRING_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}