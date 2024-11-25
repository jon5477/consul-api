package com.ecwid.consul.v1.catalog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.v1.QueryParams;

public final class CatalogNodesRequest implements QueryParameters {
	private final String datacenter;
	private final String near;
	private final Map<String, String> nodeMeta;
	private final QueryParams queryParams;

	private CatalogNodesRequest(String datacenter, String near, Map<String, String> nodeMeta, QueryParams queryParams) {
		this.datacenter = datacenter;
		this.near = near;
		this.nodeMeta = nodeMeta;
		this.queryParams = queryParams;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public String getNear() {
		return near;
	}

	public Map<String, String> getNodeMeta() {
		return nodeMeta;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public static class Builder {
		private String datacenter;
		private String near;
		private Map<String, String> nodeMeta;
		private QueryParams queryParams;

		private Builder() {
		}

		public Builder setDatacenter(String datacenter) {
			this.datacenter = datacenter;
			return this;
		}

		public Builder setNear(String near) {
			this.near = near;
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

		public CatalogNodesRequest build() {
			return new CatalogNodesRequest(datacenter, near, nodeMeta, queryParams);
		}
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	@Override
	public Map<String, String> getQueryParameters() {
		Map<String, String> params = new HashMap<>();
		if (datacenter != null) {
			params.put("dc", datacenter);
		}
		if (near != null) {
			params.put("near", near);
		}
		if (nodeMeta != null) {
			params.putAll(nodeMeta);
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
		if (!(o instanceof CatalogNodesRequest)) {
			return false;
		}
		CatalogNodesRequest that = (CatalogNodesRequest) o;
		return Objects.equals(datacenter, that.datacenter) && Objects.equals(near, that.near)
				&& Objects.equals(nodeMeta, that.nodeMeta) && Objects.equals(queryParams, that.queryParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(datacenter, near, nodeMeta, queryParams);
	}
}