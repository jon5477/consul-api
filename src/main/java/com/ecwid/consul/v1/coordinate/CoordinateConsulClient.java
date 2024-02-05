package com.ecwid.consul.v1.coordinate;

import java.util.List;

import com.ecwid.consul.json.JsonFactory;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.coordinate.model.Datacenter;
import com.ecwid.consul.v1.coordinate.model.Node;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class CoordinateConsulClient implements CoordinateClient {
	private final ConsulRawClient rawClient;

	public CoordinateConsulClient(ConsulRawClient rawClient) {
		this.rawClient = rawClient;
	}

	@Override
	public Response<List<Datacenter>> getDatacenters() {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/coordinate/datacenters");
		if (httpResponse.getStatusCode() == 200) {
			List<Datacenter> value = JsonFactory.fromJson(httpResponse.getContent(),
					new TypeReference<List<Datacenter>>() {
					});
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}

	@Override
	public Response<List<Node>> getNodes(QueryParams queryParams) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/coordinate/nodes", queryParams);
		if (httpResponse.getStatusCode() == 200) {
			List<Node> value = JsonFactory.fromJson(httpResponse.getContent(), new TypeReference<List<Node>>() {
			});
			return new Response<>(value, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}