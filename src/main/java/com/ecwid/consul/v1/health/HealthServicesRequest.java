package com.ecwid.consul.v1.health;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.v1.QueryParams;

public final class HealthServicesRequest implements QueryParameters {
	private final String datacenter;
	private final String near;
	private final String[] tags;
	private final Map<String, String> nodeMeta;
	private final String filter;
	private final boolean passing;
	private final QueryParams queryParams;
	private final String token;

	private HealthServicesRequest(String datacenter, String near, String[] tags, Map<String, String> nodeMeta,
			String filter, boolean passing, QueryParams queryParams, String token) {
		this.datacenter = datacenter;
		this.near = near;
		this.tags = tags;
		this.nodeMeta = nodeMeta;
		this.filter = filter;
		this.passing = passing;
		this.queryParams = queryParams;
		this.token = token;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public String getNear() {
		return near;
	}

	public String getTag() {
		return tags != null && tags.length > 0 ? tags[0] : null;
	}

	public String[] getTags() {
		return tags;
	}

	public Map<String, String> getNodeMeta() {
		return nodeMeta;
	}

	public String getFilter() {
		return filter;
	}

	public boolean isPassing() {
		return passing;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public String getToken() {
		return token;
	}

	public static class Builder {
		private String datacenter;
		private String near;
		private String[] tags;
		private Map<String, String> nodeMeta;
		private String filter;
		private boolean passing;
		private QueryParams queryParams;
		private String token;

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
		 * Sets the service tag to filter results by.
		 * 
		 * @see https://developer.hashicorp.com/consul/api-docs/health#query-parameters-2
		 * @param tag The service tag to filter by.
		 * @return This {@link Builder} instance for method chaining.
		 */
		@Deprecated(forRemoval = true)
		public Builder setTag(String tag) {
			this.tags = new String[] { tag };
			return this;
		}

		/**
		 * Sets the service tags to filter results by.
		 * 
		 * @deprecated Use filter with the Service.Tags selector instead.
		 * @see https://developer.hashicorp.com/consul/api-docs/health#query-parameters-2
		 * @param tags The service tags to filter by.
		 * @return This {@link Builder} instance for method chaining.
		 */
		@Deprecated(forRemoval = true)
		public Builder setTags(String[] tags) {
			this.tags = tags;
			return this;
		}

		/**
		 * Sets the node metadata to filter results by.
		 * 
		 * @deprecated Use filter with the Node.Meta selector instead.
		 * @see https://developer.hashicorp.com/consul/api-docs/health#query-parameters-2
		 * @param nodeMeta The node metadata to filter by.
		 * @return This {@link Builder} instance for method chaining.
		 */
		@Deprecated(forRemoval = true)
		public Builder setNodeMeta(Map<String, String> nodeMeta) {
			this.nodeMeta = nodeMeta != null ? Collections.unmodifiableMap(nodeMeta) : null;
			return this;
		}

		/**
		 * Set the expression used to filter the queries results prior to returning the
		 * data.
		 * 
		 * @param filter The filter expression.
		 * @return This {@link Builder} instance for method chaining.
		 */
		public Builder setFilter(String filter) {
			this.filter = filter;
			return this;
		}

		public Builder setPassing(boolean passing) {
			this.passing = passing;
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

		public HealthServicesRequest build() {
			return new HealthServicesRequest(datacenter, near, tags, nodeMeta, filter, passing, queryParams, token);
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
		// TODO Create a filter class
		if (filter != null) {
			params.put("filter", filter);
		}
		params.put("passing", String.valueOf(passing));
		if (queryParams != null) {
			params.putAll(queryParams.getQueryParameters());
		}
		if (token != null) {
			params.put("token", token);
		}
		return params;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(tags);
		result = prime * result + Objects.hash(datacenter, filter, near, nodeMeta, passing, queryParams, token);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof HealthServicesRequest)) {
			return false;
		}
		HealthServicesRequest other = (HealthServicesRequest) obj;
		return Objects.equals(datacenter, other.datacenter) && Objects.equals(filter, other.filter)
				&& Objects.equals(near, other.near) && Objects.equals(nodeMeta, other.nodeMeta)
				&& passing == other.passing && Objects.equals(queryParams, other.queryParams)
				&& Arrays.equals(tags, other.tags) && Objects.equals(token, other.token);
	}
}