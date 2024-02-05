package com.ecwid.consul.v1.catalog.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class CatalogNode {
	public static class Service {
		@JsonProperty("ID")
		@SerializedName("ID")
		private String id;

		@JsonProperty("Service")
		@SerializedName("Service")
		private String service;

		@JsonProperty("Tags")
		@SerializedName("Tags")
		private List<String> tags;

		@JsonProperty("Port")
		@SerializedName("Port")
		private Integer port;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getService() {
			return service;
		}

		public void setService(String service) {
			this.service = service;
		}

		public List<String> getTags() {
			return tags;
		}

		public void setTags(List<String> tags) {
			this.tags = tags;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		@Override
		public String toString() {
			return "Service{" + "id='" + id + '\'' + ", service='" + service + '\'' + ", tags=" + tags + ", port="
					+ port + '}';
		}
	}

	@JsonProperty("Node")
	@SerializedName("Node")
	private Node node;

	@JsonProperty("Services")
	@SerializedName("Services")
	private Map<String, Service> services;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Map<String, Service> getServices() {
		return services;
	}

	public void setServices(Map<String, Service> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "CatalogNode{" + "node=" + node + ", services=" + services + '}';
	}
}