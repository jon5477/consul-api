package com.ecwid.consul.v1.agent;

import java.util.List;
import java.util.Map;

import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.Check;
import com.ecwid.consul.v1.agent.model.Member;
import com.ecwid.consul.v1.agent.model.NewCheck;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Self;
import com.ecwid.consul.v1.agent.model.Service;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface AgentClient {
	Response<Map<String, Check>> getAgentChecks();

	Response<Map<String, Service>> getAgentServices();

	Response<List<Member>> getAgentMembers();

	Response<Self> getAgentSelf();

	Response<Self> getAgentSelf(CharSequence token);

	Response<Void> agentSetMaintenance(boolean maintenanceEnabled);

	Response<Void> agentSetMaintenance(boolean maintenanceEnabled, String reason);

	Response<Void> agentJoin(String address, boolean wan);

	Response<Void> agentForceLeave(String node);

	Response<Void> agentCheckRegister(NewCheck newCheck);

	Response<Void> agentCheckRegister(NewCheck newCheck, CharSequence token);

	Response<Void> agentCheckDeregister(String checkId);

	Response<Void> agentCheckDeregister(String checkId, CharSequence token);

	Response<Void> agentCheckPass(String checkId);

	Response<Void> agentCheckPass(String checkId, String note);

	Response<Void> agentCheckPass(String checkId, String note, CharSequence token);

	Response<Void> agentCheckWarn(String checkId);

	Response<Void> agentCheckWarn(String checkId, String note);

	Response<Void> agentCheckWarn(String checkId, String note, CharSequence token);

	Response<Void> agentCheckFail(String checkId);

	Response<Void> agentCheckFail(String checkId, String note);

	Response<Void> agentCheckFail(String checkId, String note, CharSequence token);

	Response<Void> agentServiceRegister(NewService newService);

	Response<Void> agentServiceRegister(NewService newService, CharSequence token);

	Response<Void> agentServiceDeregister(String serviceId);

	Response<Void> agentServiceDeregister(String serviceId, CharSequence token);

	Response<Void> agentServiceSetMaintenance(String serviceId, boolean maintenanceEnabled);

	Response<Void> agentServiceSetMaintenance(String serviceId, boolean maintenanceEnabled, String reason);

	Response<Void> agentReload();
}