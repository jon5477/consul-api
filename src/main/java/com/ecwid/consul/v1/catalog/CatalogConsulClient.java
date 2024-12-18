package com.ecwid.consul.v1.catalog;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.ecwid.consul.json.JsonUtil;
import com.ecwid.consul.transport.ConsulHttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Request;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.catalog.model.CatalogDeregistration;
import com.ecwid.consul.v1.catalog.model.CatalogNode;
import com.ecwid.consul.v1.catalog.model.CatalogRegistration;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import com.ecwid.consul.v1.catalog.model.Node;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class CatalogConsulClient implements CatalogClient {
	private static final TypeReference<List<String>> STRING_LIST_TYPE_REF = new TypeReference<List<String>>() {
	};
	private static final TypeReference<List<Node>> NODE_LIST_TYPE_REF = new TypeReference<List<Node>>() {
	};
	private static final TypeReference<Map<String, List<String>>> MAP_LIST_STRING_TYPE_REF = new TypeReference<Map<String, List<String>>>() {
	};
	private static final TypeReference<List<CatalogService>> CATALOG_LIST_TYPE_REF = new TypeReference<List<CatalogService>>() {
	};
	private final ConsulRawClient rawClient;

	public CatalogConsulClient(@NonNull ConsulRawClient rawClient) {
		this.rawClient = Objects.requireNonNull(rawClient, "consul raw client cannot be null");
	}

	@Override
	public Response<Void> catalogRegister(CatalogRegistration catalogRegistration) {
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/catalog/register",
				JsonUtil.toBytes(catalogRegistration));
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> catalogDeregister(CatalogDeregistration catalogDeregistration) {
		ConsulHttpResponse httpResponse = rawClient.makePutRequest("/v1/catalog/deregister",
				JsonUtil.toBytes(catalogDeregistration));
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<String>> getCatalogDatacenters() {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/catalog/datacenters");
		if (httpResponse.getStatusCode() == 200) {
			List<String> value = JsonUtil.toPOJO(httpResponse.getContent(), STRING_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Node>> getCatalogNodes(CatalogNodesRequest catalogNodesRequest) {
		Request request = new Request.Builder("/v1/catalog/nodes")
				.addQueryParameters(catalogNodesRequest.getQueryParameters()).build();
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<Node> value = JsonUtil.toPOJO(httpResponse.getContent(), NODE_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Map<String, List<String>>> getCatalogServices(CatalogServicesRequest catalogServicesRequest) {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/catalog/services", catalogServicesRequest);
		if (httpResponse.getStatusCode() == 200) {
			Map<String, List<String>> value = JsonUtil.toPOJO(httpResponse.getContent(), MAP_LIST_STRING_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<CatalogService>> getCatalogService(String serviceName,
			CatalogServiceRequest catalogServiceRequest) {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/catalog/service/" + serviceName,
				catalogServiceRequest);
		if (httpResponse.getStatusCode() == 200) {
			List<CatalogService> value = JsonUtil.toPOJO(httpResponse.getContent(), CATALOG_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<CatalogNode> getCatalogNode(String nodeName, QueryParams queryParams) {
		ConsulHttpResponse httpResponse = rawClient.makeGetRequest("/v1/catalog/node/" + nodeName, queryParams);
		if (httpResponse.getStatusCode() == 200) {
			CatalogNode catalogNode = JsonUtil.toPOJO(httpResponse.getContent(), CatalogNode.class);
			return new Response<>(catalogNode, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}