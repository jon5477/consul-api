package com.ecwid.consul.v1.query;

import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.ecwid.consul.json.JsonUtil;
import com.ecwid.consul.transport.HttpResponse;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.query.model.QueryExecution;

public final class QueryConsulClient implements QueryClient {
	private final ConsulRawClient rawClient;

	public QueryConsulClient(@NonNull ConsulRawClient rawClient) {
		this.rawClient = Objects.requireNonNull(rawClient, "consul raw client cannot be null");
	}

	@Override
	public Response<QueryExecution> executePreparedQuery(String uuid, QueryParams queryParams) {
		HttpResponse httpResponse = rawClient.makeGetRequest("/v1/query/" + uuid + "/execute", queryParams);
		if (httpResponse.getStatusCode() == 200) {
			QueryExecution queryExecution = JsonUtil.toPOJO(httpResponse.getContent(), QueryExecution.class);
			return new Response<>(queryExecution, httpResponse);
		} else {
			throw new OperationException(httpResponse);
		}
	}
}