package com.ecwid.consul.v1.catalog;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.v1.Filter;
import com.ecwid.consul.v1.QueryParams;

public final class CatalogServiceRequest implements QueryParameters {
	private final String datacenter;
	private final String near;
	private final Filter filter;
	private final QueryParams queryParams;

	private CatalogServiceRequest(String datacenter, String near, Filter filter, QueryParams queryParams) {
		this.datacenter = datacenter;
		this.near = near;
		this.filter = filter;
		this.queryParams = queryParams;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public String getNear() {
		return near;
	}

	public Filter getFilter() {
		return filter;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public static class Builder {
		private String datacenter;
		private String near;
		private Filter filter;
		private QueryParams queryParams;

		public Builder setDatacenter(String datacenter) {
			this.datacenter = datacenter;
			return this;
		}

		public Builder setNear(String near) {
			this.near = near;
			return this;
		}

		public Builder setFilters(Filter filter) {
			this.filter = filter;
			return this;
		}

		public Builder setQueryParams(QueryParams queryParams) {
			this.queryParams = queryParams;
			return this;
		}

		public CatalogServiceRequest build() {
			return new CatalogServiceRequest(datacenter, near, filter, queryParams);
		}
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
		if (filter != null) {
			params.put("filter", filter.toString());
		}
		if (queryParams != null) {
			params.putAll(queryParams.getQueryParameters());
		}
		return params;
	}

	@Override
	public int hashCode() {
		return Objects.hash(datacenter, filter, near, queryParams);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CatalogServiceRequest)) {
			return false;
		}
		CatalogServiceRequest other = (CatalogServiceRequest) obj;
		return Objects.equals(datacenter, other.datacenter) && Objects.equals(filter, other.filter)
				&& Objects.equals(near, other.near) && Objects.equals(queryParams, other.queryParams);
	}
}