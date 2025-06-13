package com.ecwid.consul.v1.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.Utils;
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

		/**
		 * Specifies a desired node metadata key/value pair of the form key:value and
		 * will filter the results to nodes with the specified key/value pairs.
		 * 
		 * @param nodeMeta
		 * @deprecated Use filter with the NodeMeta selector instead. This parameter
		 *             will be removed in a future version of Consul. Specifies a
		 *             desired node metadata key/value pair of the form key:value. This
		 *             parameter can be specified multiple times, and filters the
		 *             results to nodes with the specified key/value pairs. (Deprecated
		 *             since Consul 1.9)
		 * @return
		 */
		@Deprecated
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
	public Map<String, List<CharSequence>> getQueryParameters() {
		Map<String, List<CharSequence>> params = new HashMap<>();
		if (datacenter != null) {
			params.put("dc", Utils.asList(datacenter));
		}
		if (near != null) {
			params.put("near", Utils.asList(near));
		}
		if (nodeMeta != null) {
			List<CharSequence> nodeMetaList = nodeMeta.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
					.collect(Collectors.toCollection(ArrayList::new));
			params.put("node-meta", nodeMetaList);
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