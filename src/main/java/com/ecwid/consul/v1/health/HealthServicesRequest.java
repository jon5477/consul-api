package com.ecwid.consul.v1.health;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.v1.Filter;
import com.ecwid.consul.v1.QueryParams;

public final class HealthServicesRequest implements QueryParameters {
	private final String datacenter;
	private final String near;
	private final Filter filter;
	private final boolean passing;
	private final QueryParams queryParams;
	private final boolean cached;

	private HealthServicesRequest(Builder b) {
		this.datacenter = b.datacenter;
		this.near = b.near;
		this.filter = b.filter;
		this.passing = b.passing;
		this.queryParams = b.queryParams;
		this.cached = b.cached;
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

	public boolean isPassing() {
		return passing;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public boolean isCached() {
		return cached;
	}

	public static class Builder {
		private String datacenter;
		private String near;
		private Filter filter;
		private boolean passing;
		private QueryParams queryParams;
		private boolean cached;

		public Builder setDatacenter(String datacenter) {
			this.datacenter = datacenter;
			return this;
		}

		public Builder setNear(String near) {
			this.near = near;
			return this;
		}

		/**
		 * Set the {@link Filter} used to filter the queries results prior to returning
		 * the data.
		 * 
		 * @param filter The {@link Filter} instance.
		 * @return This {@link Builder} instance for method chaining.
		 */
		public Builder setFilter(Filter filter) {
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

		public Builder setCached(boolean cached) {
			this.cached = cached;
			return this;
		}

		public HealthServicesRequest build() {
			return new HealthServicesRequest(this);
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
		if (filter != null) {
			params.put("filter", filter.toString());
		}
		params.put("passing", String.valueOf(passing));
		if (queryParams != null) {
			params.putAll(queryParams.getQueryParameters());
		}
		if (cached) {
			params.put("cached", null);
		}
		return params;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cached, datacenter, filter, near, passing, queryParams);
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
		return cached == other.cached && Objects.equals(datacenter, other.datacenter)
				&& Objects.equals(filter, other.filter) && Objects.equals(near, other.near) && passing == other.passing
				&& Objects.equals(queryParams, other.queryParams);
	}
}