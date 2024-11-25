package com.ecwid.consul.v1.catalog;

import java.util.List;
import java.util.Map;

import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.transport.TLSConfig;
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

	public CatalogConsulClient(ConsulRawClient rawClient) {
		this.rawClient = rawClient;
	}

	public CatalogConsulClient() {
		this(new ConsulRawClient());
	}

	public CatalogConsulClient(TLSConfig tlsConfig) {
		this(new ConsulRawClient(tlsConfig));
	}

	public CatalogConsulClient(String agentHost) {
		this(new ConsulRawClient(agentHost));
	}

	public CatalogConsulClient(String agentHost, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, tlsConfig));
	}

	public CatalogConsulClient(String agentHost, int agentPort) {
		this(new ConsulRawClient(agentHost, agentPort));
	}

	public CatalogConsulClient(String agentHost, int agentPort, TLSConfig tlsConfig) {
		this(new ConsulRawClient(agentHost, agentPort, tlsConfig));
	}

	@Override
	public Response<Void> catalogRegister(CatalogRegistration catalogRegistration) {
		return catalogRegister(catalogRegistration, null);
	}

	@Override
	public Response<Void> catalogRegister(CatalogRegistration catalogRegistration, CharSequence token) {
		HttpResponse httpResponse = rawClient.makePutRequest("/v1/catalog/register",
				JsonFactory.toBytes(catalogRegistration));
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Void> catalogDeregister(CatalogDeregistration catalogDeregistration) {
		return catalogDeregister(catalogDeregistration, null);
	}

	@Override
	public Response<Void> catalogDeregister(CatalogDeregistration catalogDeregistration, CharSequence token) {
		HttpResponse httpResponse = rawClient.makePutRequest("/v1/catalog/deregister",
				JsonFactory.toBytes(catalogDeregistration));
		if (httpResponse.getStatusCode() == 200) {
			return new Response<>(null, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<String>> getCatalogDatacenters() {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/catalog/datacenters");
		if (httpResponse.getStatusCode() == 200) {
			List<String> value = JsonFactory.toPOJO(httpResponse.getContent(), STRING_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Node>> getCatalogNodes(QueryParams queryParams) {
		CatalogNodesRequest request = CatalogNodesRequest.newBuilder().setQueryParams(queryParams).build();
		return getCatalogNodes(request);
	}

	@Override
	public Response<List<Node>> getCatalogNodes(CatalogNodesRequest catalogNodesRequest) {
		Request request = Request.Builder.newBuilder().setEndpoint("/v1/catalog/nodes")
				.addQueryParameters(catalogNodesRequest.getQueryParameters()).build();
		HttpResponse httpResponse = rawClient.makeGetRequest(request);
		if (httpResponse.getStatusCode() == 200) {
			List<Node> value = JsonFactory.toPOJO(httpResponse.getContent(), NODE_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<Map<String, List<String>>> getCatalogServices(QueryParams queryParams) {
		return getCatalogServices(queryParams, null);
	}

	@Override
	public Response<Map<String, List<String>>> getCatalogServices(QueryParams queryParams, CharSequence token) {
		CatalogServicesRequest request = CatalogServicesRequest.newBuilder().setQueryParams(queryParams).build();
		return getCatalogServices(request);
	}

	@Override
	public Response<Map<String, List<String>>> getCatalogServices(CatalogServicesRequest catalogServicesRequest) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/catalog/services", catalogServicesRequest);
		if (httpResponse.getStatusCode() == 200) {
			Map<String, List<String>> value = JsonFactory.toPOJO(httpResponse.getContent(), MAP_LIST_STRING_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<CatalogService>> getCatalogService(String serviceName, QueryParams queryParams) {
		return getCatalogService(serviceName, (String) null, queryParams, null);
	}

	@Override
	public Response<List<CatalogService>> getCatalogService(String serviceName, QueryParams queryParams,
			CharSequence token) {
		return getCatalogService(serviceName, (String) null, queryParams, token);
	}

	@Override
	public Response<List<CatalogService>> getCatalogService(String serviceName, String tag, QueryParams queryParams) {
		return getCatalogService(serviceName, tag, queryParams, null);
	}

	@Override
	public Response<List<CatalogService>> getCatalogService(String serviceName, String tag, QueryParams queryParams,
			CharSequence token) {
		return getCatalogService(serviceName, new String[] { tag }, queryParams, null);
	}

	@Override
	public Response<List<CatalogService>> getCatalogService(String serviceName, String[] tag, QueryParams queryParams,
			CharSequence token) {
		CatalogServiceRequest request = CatalogServiceRequest.newBuilder().setTags(tag).setQueryParams(queryParams)
				.build();
		return getCatalogService(serviceName, request);
	}

	@Override
	public Response<List<CatalogService>> getCatalogService(String serviceName,
			CatalogServiceRequest catalogServiceRequest) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/catalog/service/" + serviceName,
				catalogServiceRequest);
		if (httpResponse.getStatusCode() == 200) {
			List<CatalogService> value = JsonFactory.toPOJO(httpResponse.getContent(), CATALOG_LIST_TYPE_REF);
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<CatalogNode> getCatalogNode(String nodeName, QueryParams queryParams) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/catalog/node/" + nodeName, queryParams);
		if (httpResponse.getStatusCode() == 200) {
			CatalogNode catalogNode = JsonFactory.toPOJO(httpResponse.getContent(), CatalogNode.class);
			return new Response<>(catalogNode, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}