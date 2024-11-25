package com.ecwid.consul.v1.catalog;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.v1.QueryParams;

public final class CatalogServiceRequest implements QueryParameters {
	private final String datacenter;
	@Deprecated(forRemoval = true)
	private final String[] tags;
	private final String near;
	@Deprecated(forRemoval = true)
	private final Map<String, String> nodeMeta;
	private final QueryParams queryParams;
	private final String token;

	private CatalogServiceRequest(String datacenter, String[] tags, String near, Map<String, String> nodeMeta,
			QueryParams queryParams, String token) {
		this.datacenter = datacenter;
		this.tags = tags;
		this.near = near;
		this.nodeMeta = nodeMeta;
		this.queryParams = queryParams;
		this.token = token;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public String getTag() {
		return tags != null && tags.length > 0 ? tags[0] : null;
	}

	public String[] getTags() {
		return tags;
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

	public String getToken() {
		return token;
	}

	public static class Builder {
		private String datacenter;
		private String[] tags;
		private String near;
		private Map<String, String> nodeMeta;
		private QueryParams queryParams;
		private String token;

		private Builder() {
		}

		public Builder setDatacenter(String datacenter) {
			this.datacenter = datacenter;
			return this;
		}

		public Builder setTag(String tag) {
			this.tags = new String[] { tag };
			return this;
		}

		public Builder setTags(String[] tags) {
			this.tags = tags;
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

		public Builder setToken(String token) {
			this.token = token;
			return this;
		}

		public CatalogServiceRequest build() {
			return new CatalogServiceRequest(datacenter, tags, near, nodeMeta, queryParams, token);
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
		if (!(o instanceof CatalogServiceRequest)) {
			return false;
		}
		CatalogServiceRequest that = (CatalogServiceRequest) o;
		return Objects.equals(datacenter, that.datacenter) && Arrays.equals(tags, that.tags)
				&& Objects.equals(near, that.near) && Objects.equals(nodeMeta, that.nodeMeta)
				&& Objects.equals(queryParams, that.queryParams) && Objects.equals(token, that.token);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(datacenter, near, nodeMeta, queryParams, token);
		result = 31 * result + Arrays.hashCode(tags);
		return result;
	}
}