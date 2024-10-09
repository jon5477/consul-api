package com.ecwid.consul.v1.catalog;

import java.util.List;
import java.util.Map;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.catalog.model.CatalogDeregistration;
import com.ecwid.consul.v1.catalog.model.CatalogNode;
import com.ecwid.consul.v1.catalog.model.CatalogRegistration;
import com.ecwid.consul.v1.catalog.model.Node;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public interface CatalogClient {
	Response<Void> catalogRegister(CatalogRegistration catalogRegistration);

	Response<Void> catalogRegister(CatalogRegistration catalogRegistration, String token);

	// -------------------------------------------------------------------------------

	Response<Void> catalogDeregister(CatalogDeregistration catalogDeregistration);

	Response<Void> catalogDeregister(CatalogDeregistration catalogDeregistration, String token);

	// -------------------------------------------------------------------------------

	Response<List<String>> getCatalogDatacenters();

	// -------------------------------------------------------------------------------

	Response<List<Node>> getCatalogNodes(CatalogNodesRequest catalogNodesRequest);

	// -------------------------------------------------------------------------------

	Response<Map<String, List<String>>> getCatalogServices(CatalogServicesRequest catalogServicesRequest);

	// -------------------------------------------------------------------------------

	Response<List<com.ecwid.consul.v1.catalog.model.CatalogService>> getCatalogService(String serviceName,
			CatalogServiceRequest catalogServiceRequest);

	// -------------------------------------------------------------------------------

	Response<CatalogNode> getCatalogNode(String nodeName, QueryParams queryParams);
}