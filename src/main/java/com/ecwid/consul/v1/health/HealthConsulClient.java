package com.ecwid.consul.v1.health;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.ecwid.consul.json.JsonUtil;
import com.ecwid.consul.transport.ConsulHttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class HealthConsulClient implements HealthClient {
	private static final TypeReference<List<Check>> CHECK_LIST_TYPE_REF = new TypeReference<List<Check>>() {
	};
	private static final TypeReference<List<HealthService>> HEALTH_SERVICE_LIST_TYPE_REF = new TypeReference<List<HealthService>>() {
	};
	private final ConsulRawClient rawClient;

	public HealthConsulClient(@NonNull ConsulRawClient rawClient) {
		this.rawClient = Objects.requireNonNull(rawClient, "consul raw client cannot be null");
	}

	@Override
	public Response<List<Check>> getHealthChecksForNode(String nodeName, QueryParams queryParams) {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/node/" + nodeName, queryParams);
		if (httpResponse.getStatusCode() == 200) {
			List<Check> value = JsonUtil.toPOJO(httpResponse.getContent(), CHECK_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Check>> getHealthChecksForService(String serviceName,
			HealthChecksForServiceRequest healthChecksForServiceRequest) {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/checks/" + serviceName,
				healthChecksForServiceRequest);
		if (httpResponse.getStatusCode() == 200) {
			List<Check> value = JsonUtil.toPOJO(httpResponse.getContent(), CHECK_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<HealthService>> getHealthServices(String serviceName,
			HealthServicesRequest healthServicesRequest) {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/service/" + serviceName,
				healthServicesRequest);
		if (httpResponse.getStatusCode() == 200) {
			List<HealthService> value = JsonUtil.toPOJO(httpResponse.getContent(), HEALTH_SERVICE_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Check>> getHealthChecksState(QueryParams queryParams) {
		return getHealthChecksState(null, queryParams);
	}

	@Override
	public Response<List<Check>> getHealthChecksState(Check.CheckStatus checkStatus, QueryParams queryParams) {
		String status = checkStatus == null ? "any" : checkStatus.name().toLowerCase(Locale.ROOT);
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/state/" + status, queryParams);
		if (httpResponse.getStatusCode() == 200) {
			List<Check> value = JsonUtil.toPOJO(httpResponse.getContent(), CHECK_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}