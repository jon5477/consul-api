package com.ecwid.consul.v1.health;

import java.util.List;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface HealthClient {
	Response<List<Check>> getHealthChecksForNode(String nodeName, QueryParams queryParams);

	// -------------------------------------------------------------------------------

	Response<List<Check>> getHealthChecksForService(String serviceName,
			HealthChecksForServiceRequest healthChecksForServiceRequest);

	// -------------------------------------------------------------------------------

	Response<List<HealthService>> getHealthServices(String serviceName, HealthServicesRequest healthServicesRequest);

	// -------------------------------------------------------------------------------

	Response<List<Check>> getHealthChecksState(QueryParams queryParams);

	Response<List<Check>> getHealthChecksState(Check.CheckStatus checkStatus, QueryParams queryParams);
}