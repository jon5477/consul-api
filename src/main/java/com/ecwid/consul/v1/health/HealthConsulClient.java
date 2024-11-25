package com.ecwid.consul.v1.health;

import java.util.List;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
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
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/node/" + nodeName, queryParams);
		if (httpResponse.getStatusCode() == 200) {
			List<Check> value = JsonFactory.toPOJO(httpResponse.getContent(), CHECK_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Check>> getHealthChecksForService(String serviceName, QueryParams queryParams) {
		HealthChecksForServiceRequest request = HealthChecksForServiceRequest.newBuilder().setQueryParams(queryParams)
				.build();
		return getHealthChecksForService(serviceName, request);
	}

	@Override
	public Response<List<Check>> getHealthChecksForService(String serviceName,
			HealthChecksForServiceRequest healthChecksForServiceRequest) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/checks/" + serviceName,
				healthChecksForServiceRequest);
		if (httpResponse.getStatusCode() == 200) {
			List<Check> value = JsonFactory.toPOJO(httpResponse.getContent(), CHECK_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<HealthService>> getHealthServices(String serviceName, boolean onlyPassing,
			QueryParams queryParams) {
		return getHealthServices(serviceName, (String) null, onlyPassing, queryParams, null);
	}

	@Override
	public Response<List<HealthService>> getHealthServices(String serviceName, boolean onlyPassing,
			QueryParams queryParams, String token) {
		return getHealthServices(serviceName, (String) null, onlyPassing, queryParams, token);
	}

	@Override
	public Response<List<HealthService>> getHealthServices(String serviceName, String tag, boolean onlyPassing,
			QueryParams queryParams) {
		return getHealthServices(serviceName, tag, onlyPassing, queryParams, null);
	}

	@Override
	public Response<List<HealthService>> getHealthServices(String serviceName, String tag, boolean onlyPassing,
			QueryParams queryParams, String token) {
		return getHealthServices(serviceName, new String[] { tag }, onlyPassing, queryParams, token);
	}

	@Override
	public Response<List<HealthService>> getHealthServices(String serviceName, String[] tags, boolean onlyPassing,
			QueryParams queryParams, String token) {
		HealthServicesRequest request = HealthServicesRequest.newBuilder().setTags(tags).setPassing(onlyPassing)
				.setQueryParams(queryParams).setToken(token).build();
		return getHealthServices(serviceName, request);
	}

	@Override
	public Response<List<HealthService>> getHealthServices(String serviceName,
			HealthServicesRequest healthServicesRequest) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/service/" + serviceName,
				healthServicesRequest);
		if (httpResponse.getStatusCode() == 200) {
			List<HealthService> value = JsonFactory.toPOJO(httpResponse.getContent(), HEALTH_SERVICE_LIST_TYPE_REF);
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
		String status = checkStatus == null ? "any" : checkStatus.name().toLowerCase();
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/state/" + status, queryParams);
		if (httpResponse.getStatusCode() == 200) {
			List<Check> value = JsonFactory.toPOJO(httpResponse.getContent(), CHECK_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}