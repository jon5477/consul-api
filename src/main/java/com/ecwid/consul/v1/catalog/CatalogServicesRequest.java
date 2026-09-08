package com.ecwid.consul.v1.catalog;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.Utils;
import com.ecwid.consul.v1.Filter;
import com.ecwid.consul.v1.QueryParams;

public final class CatalogServicesRequest implements QueryParameters {
	private final String datacenter;
	private final Map<String, String> nodeMeta;
	private final Filter filter;
	private final QueryParams queryParams;

	public CatalogServicesRequest(String datacenter, Map<String, String> nodeMeta, Filter filter,
			QueryParams queryParams) {
		this.datacenter = datacenter;
		this.nodeMeta = nodeMeta;
		this.filter = filter;
		this.queryParams = queryParams;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public Map<String, String> getNodeMeta() {
		return nodeMeta;
	}

	public Filter getFilter() {
		return filter;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public static class Builder {
		private String datacenter;
		private Map<String, String> nodeMeta;
		private Filter filter;
		private QueryParams queryParams;

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

		public Builder setFilter(Filter filter) {
			this.filter = filter;
			return this;
		}

		public Builder setQueryParams(QueryParams queryParams) {
			this.queryParams = queryParams;
			return this;
		}

		public CatalogServicesRequest build() {
			return new CatalogServicesRequest(datacenter, nodeMeta, filter, queryParams);
		}
	}

	public static Builder newBuilder() {
		return new CatalogServicesRequest.Builder();
	}

	@Override
	public Map<String, List<CharSequence>> getQueryParameters() {
		Map<String, List<CharSequence>> params = new HashMap<>();
		if (datacenter != null) {
			params.put("dc", Utils.asList(datacenter));
		}
		if (filter != null) {
			params.putAll(filter.getQueryParameters());
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
				&& Objects.equals(filter, that.filter) && Objects.equals(queryParams, that.queryParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(datacenter, nodeMeta, filter, queryParams);
	}
}