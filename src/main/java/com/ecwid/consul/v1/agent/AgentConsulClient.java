package com.ecwid.consul.v1.agent;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.SingleQueryParameters;
import com.ecwid.consul.json.JsonUtil;
import com.ecwid.consul.transport.ConsulHttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.Request;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.Check;
import com.ecwid.consul.v1.agent.model.Member;
import com.ecwid.consul.v1.agent.model.NewCheck;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Self;
import com.ecwid.consul.v1.agent.model.Service;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class AgentConsulClient implements AgentClient {
	private static final TypeReference<Map<String, Check>> CHECK_MAP_TYPE_REF = new TypeReference<Map<String, Check>>() {
	};
	private static final TypeReference<Map<String, Service>> SERVICE_MAP_TYPE_REF = new TypeReference<Map<String, Service>>() {
	};
	private static final TypeReference<List<Member>> MEMBER_LIST_TYPE_REF = new TypeReference<List<Member>>() {
	};
	private final ConsulRawClient rawClient;

	public AgentConsulClient(@NonNull ConsulRawClient rawClient) {
		this.rawClient = Objects.requireNonNull(rawClient, "consul raw client cannot be null");
	}

	@Override
	public Response<Map<String, Check>> getAgentChecks() {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/agent/checks");
		if (httpResponse.getStatusCode() == 200) {
			Map<String, Check> value = JsonUtil.toPOJO(httpResponse.getContent(), CHECK_MAP_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Map<String, Service>> getAgentServices() {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/agent/services");
		if (httpResponse.getStatusCode() == 200) {
			Map<String, Service> agentServices = JsonUtil.toPOJO(httpResponse.getContent(), SERVICE_MAP_TYPE_REF);
			return new Response<>(agentServices, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Member>> getAgentMembers() {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/agent/members");
		if (httpResponse.getStatusCode() == 200) {
			List<Member> members = JsonUtil.toPOJO(httpResponse.getContent(), MEMBER_LIST_TYPE_REF);
			return new Response<>(members, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Self> getAgentSelf() {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/agent/self");
		if (httpResponse.getStatusCode() == 200) {
			Self self = JsonUtil.toPOJO(httpResponse.getContent(), Self.class);
			return new Response<>(self, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentSetMaintenance(boolean maintenanceEnabled) {
		return agentSetMaintenance(maintenanceEnabled, null);
	}

	@Override
	public Response<Void> agentSetMaintenance(boolean maintenanceEnabled, String reason) {
		QueryParameters maintenanceParameter = new SingleQueryParameters("enable",
				Boolean.toString(maintenanceEnabled));
		QueryParameters reasonParameter = reason != null ? new SingleQueryParameters("reason", reason) : null;
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/maintenance", null, maintenanceParameter,
				reasonParameter);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentJoin(String address, boolean wan) {
		QueryParameters wanParams = wan ? new SingleQueryParameters("wan", "1") : null;
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/join/" + address, null, wanParams);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentForceLeave(String node) {
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/force-leave/" + node, null);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentCheckRegister(NewCheck newCheck) {
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/check/register",
				JsonUtil.toBytes(newCheck));
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentCheckDeregister(String checkId) {
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/check/deregister/" + checkId, null);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentCheckPass(String checkId) {
		return agentCheckPass(checkId, null);
	}

	@Override
	public Response<Void> agentCheckPass(String checkId, String note) {
		QueryParameters noteParameter = note != null ? new SingleQueryParameters("note", note) : null;
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/check/pass/" + checkId, null,
				noteParameter);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentCheckWarn(String checkId) {
		return agentCheckWarn(checkId, null);
	}

	@Override
	public Response<Void> agentCheckWarn(String checkId, String note) {
		QueryParameters noteParameter = note != null ? new SingleQueryParameters("note", note) : null;
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/check/warn/" + checkId, null,
				noteParameter);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentCheckFail(String checkId) {
		return agentCheckFail(checkId, null);
	}

	@Override
	public Response<Void> agentCheckFail(String checkId, String note) {
		QueryParameters noteParameter = note != null ? new SingleQueryParameters("note", note) : null;
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/check/fail/" + checkId, null,
				noteParameter);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentServiceRegister(NewService newService) {
		Request.Builder request = new Request.Builder("/v1/agent/service/register")
				.setContent(JsonUtil.toBytes(newService));
		ConsulHttpResponse httpResponse = rawClient.makePutRequest(request.build());
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentServiceDeregister(String serviceId) {
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/service/deregister/" + serviceId, null);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentServiceSetMaintenance(String serviceId, boolean maintenanceEnabled) {
		return agentServiceSetMaintenance(serviceId, maintenanceEnabled, null);
	}

	@Override
	public Response<Void> agentServiceSetMaintenance(String serviceId, boolean maintenanceEnabled, String reason) {
		QueryParameters maintenanceParameter = new SingleQueryParameters("enable",
				Boolean.toString(maintenanceEnabled));
		QueryParameters reasonParameter = reason != null ? new SingleQueryParameters("reason", reason) : null;
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/service/maintenance/" + serviceId, null,
				maintenanceParameter, reasonParameter);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> agentReload() {
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/agent/reload", null);
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}