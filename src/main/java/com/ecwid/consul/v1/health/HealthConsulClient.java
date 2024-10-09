package com.ecwid.consul.v1.health;

import java.util.List;

import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.transport.TLSConfig;
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
	private final ConsulRawClient rawClient;

	public HealthConsulClient(ConsulRawClient rawClient) {
		this.rawClient = rawClient;
	}

	public HealthConsulClient() {
		this(new ConsulRawClient());
	}

	public HealthConsulClient(TLSConfig tlsConfig) {
		this(new ConsulRawClient(tlsConfig));
	}

	public HealthConsulClient(String agentHost) {
		this(new ConsulRawClient(agentHost));
	}

	public HealthConsulClient(String agentHost, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, tlsConfig));
	}

	public HealthConsulClient(String agentHost, int agentPort) {
		this(new ConsulRawClient(agentHost, agentPort));
	}

	public HealthConsulClient(String agentHost, int agentPort, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, agentPort, tlsConfig));
	}

	@Override
	public Response<List<Check>> getHealthChecksForNode(String nodeName, QueryParams queryParams) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/node/" + nodeName, queryParams);
		if (httpResponse.getStatusCode() == 200) {
			List<Check> value = JsonFactory.fromJson(httpResponse.getContent(), new TypeReference<List<Check>>() {
			});
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Check>> getHealthChecksForService(String serviceName,
			HealthChecksForServiceRequest healthChecksForServiceRequest) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/checks/" + serviceName,
				healthChecksForServiceRequest.asUrlParameters());
		if (httpResponse.getStatusCode() == 200) {
			List<Check> value = JsonFactory.fromJson(httpResponse.getContent(), new TypeReference<List<Check>>() {
			});
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<HealthService>> getHealthServices(String serviceName,
			HealthServicesRequest healthServicesRequest) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/health/service/" + serviceName,
				healthServicesRequest.asUrlParameters());
		if (httpResponse.getStatusCode() == 200) {
			List<HealthService> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<HealthService>>() {
					});
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
			List<Check> value = JsonFactory.fromJson(httpResponse.getContent(), new TypeReference<List<Check>>() {
			});
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}