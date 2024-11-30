package com.ecwid.consul.v1.event.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class EventParams implements QueryParameters {
	private String name;
	private String service;
	private String tag;
	private String node;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	@Override
	public Map<String, String> getQueryParameters() {
		Map<String, String> result = new HashMap<>();
		if (name != null) {
			result.put("name", name);
		}
		if (service != null) {
			result.put("service", service);
		}
		if (tag != null) {
			result.put("tag", tag);
		}
		if (node != null) {
			result.put("node", node);
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof EventParams)) {
			return false;
		}
		EventParams that = (EventParams) o;
		return Objects.equals(name, that.name) && Objects.equals(service, that.service) && Objects.equals(tag, that.tag)
				&& Objects.equals(node, that.node);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, service, tag, node);
	}
}
