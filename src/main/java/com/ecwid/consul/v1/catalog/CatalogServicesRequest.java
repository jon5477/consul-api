package com.ecwid.consul.v1.catalog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.v1.QueryParams;

public final class CatalogServicesRequest implements QueryParameters {
	private final String datacenter;
	private final Map<String, String> nodeMeta;
	private final QueryParams queryParams;
	private final String token;

	public CatalogServicesRequest(String datacenter, Map<String, String> nodeMeta, QueryParams queryParams,
			String token) {
		this.datacenter = datacenter;
		this.nodeMeta = nodeMeta;
		this.queryParams = queryParams;
		this.token = token;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public Map<String, String> getNodeMeta() {
		return nodeMeta;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public String getToken() {
		return token;
	}

	public static class Builder {
		private String datacenter;
		private Map<String, String> nodeMeta;
		private QueryParams queryParams;
		private String token;

		private Builder() {
		}

		public Builder setDatacenter(String datacenter) {
			this.datacenter = datacenter;
			return this;
		}

		public Builder setNodeMeta(Map<String, String> nodeMeta) {
			if (nodeMeta == null) {
				this.nodeMeta = null;
			} else {
				this.nodeMeta = Collections.unmodifiableMap(nodeMeta);
			}
			return this;
		}

		public Builder setQueryParams(QueryParams queryParams) {
			this.queryParams = queryParams;
			return this;
		}

		public Builder setToken(String token) {
			this.token = token;
			return this;
		}

		public CatalogServicesRequest build() {
			return new CatalogServicesRequest(datacenter, nodeMeta, queryParams, token);
		}
	}

	public static Builder newBuilder() {
		return new CatalogServicesRequest.Builder();
	}

	@Override
	public Map<String, String> getQueryParameters() {
		Map<String, String> params = new HashMap<>();
		if (datacenter != null) {
			params.put("dc", datacenter);
		}
		if (queryParams != null) {
			params.putAll(queryParams.getQueryParameters());
		}
		return params;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CatalogServicesRequest)) {
			return false;
		}
		CatalogServicesRequest that = (CatalogServicesRequest) o;
		return Objects.equals(datacenter, that.datacenter) && Objects.equals(nodeMeta, that.nodeMeta)
				&& Objects.equals(queryParams, that.queryParams) && Objects.equals(token, that.token);
	}

	@Override
	public int hashCode() {
		return Objects.hash(datacenter, nodeMeta, queryParams, token);
	}
}