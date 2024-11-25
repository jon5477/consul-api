package com.ecwid.consul.v1.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.v1.QueryParams;

public final class EventListRequest implements QueryParameters {
	private final String name;
	private final String node;
	private final String service;
	private final String tag;
	private final QueryParams queryParams;

	private EventListRequest(String name, String node, String service, String tag, QueryParams queryParams) {
		this.name = name;
		this.node = node;
		this.service = service;
		this.tag = tag;
		this.queryParams = queryParams;
	}

	public String getName() {
		return name;
	}

	public String getNode() {
		return node;
	}

	public String getService() {
		return service;
	}

	public String getTag() {
		return tag;
	}

	public QueryParams getQueryParams() {
		return queryParams;
	}

	public static class Builder {
		private String name;
		private String node;
		private String service;
		private String tag;
		private QueryParams queryParams;

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setNode(String node) {
			this.node = node;
			return this;
		}

		public Builder setService(String service) {
			this.service = service;
			return this;
		}

		public Builder setTag(String tag) {
			this.tag = tag;
			return this;
		}

		public Builder setQueryParams(QueryParams queryParams) {
			this.queryParams = queryParams;
			return this;
		}

		public EventListRequest build() {
			return new EventListRequest(name, node, service, tag, queryParams);
		}
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	@Override
	public Map<String, String> getQueryParameters() {
		Map<String, String> params = new HashMap<>();
		if (name != null) {
			params.put("name", name);
		}
		if (node != null) {
			params.put("node", node);
		}
		if (service != null) {
			params.put("service", service);
		}
		if (tag != null) {
			params.put("tag", tag);
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
		if (!(o instanceof EventListRequest)) {
			return false;
		}
		EventListRequest that = (EventListRequest) o;
		return Objects.equals(name, that.name) && Objects.equals(node, that.node)
				&& Objects.equals(service, that.service) && Objects.equals(tag, that.tag)
				&& Objects.equals(queryParams, that.queryParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, node, service, tag, queryParams);
	}
}