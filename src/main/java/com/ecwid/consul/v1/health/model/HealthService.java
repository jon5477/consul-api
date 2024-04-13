package com.ecwid.consul.v1.health.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class HealthService {
	public static class Node {
		@JsonProperty("ID")
		private String id;

		@JsonProperty("Node")
		private String node;

		@JsonProperty("Address")
		private String address;

		@JsonProperty("Datacenter")
		private String datacenter;

		@JsonProperty("TaggedAddresses")
		private Map<String, String> taggedAddresses;

		@JsonProperty("Meta")
		private Map<String, String> meta;

		@JsonProperty("CreateIndex")
		private Long createIndex;

		@JsonProperty("ModifyIndex")
		private Long modifyIndex;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getNode() {
			return node;
		}

		public void setNode(String node) {
			this.node = node;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getDatacenter() {
			return datacenter;
		}

		public void setDatacenter(String datacenter) {
			this.datacenter = datacenter;
		}

		public Map<String, String> getTaggedAddresses() {
			return taggedAddresses;
		}

		public void setTaggedAddresses(Map<String, String> taggedAddresses) {
			this.taggedAddresses = taggedAddresses;
		}

		public Map<String, String> getMeta() {
			return meta;
		}

		public void setMeta(Map<String, String> meta) {
			this.meta = meta;
		}

		public Long getCreateIndex() {
			return createIndex;
		}

		public void setCreateIndex(Long createIndex) {
			this.createIndex = createIndex;
		}

		public Long getModifyIndex() {
			return modifyIndex;
		}

		public void setModifyIndex(Long modifyIndex) {
			this.modifyIndex = modifyIndex;
		}

		@Override
		public String toString() {
			return "Node{" + "id='" + id + '\'' + ", node='" + node + '\'' + ", address='" + address + '\''
					+ ", datacenter='" + datacenter + '\'' + ", taggedAddresses=" + taggedAddresses + ", meta=" + meta
					+ ", createIndex=" + createIndex + ", modifyIndex=" + modifyIndex + '}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Node node1 = (Node) o;
			return Objects.equals(id, node1.id) && Objects.equals(node, node1.node)
					&& Objects.equals(address, node1.address) && Objects.equals(datacenter, node1.datacenter)
					&& Objects.equals(taggedAddresses, node1.taggedAddresses) && Objects.equals(meta, node1.meta)
					&& Objects.equals(createIndex, node1.createIndex) && Objects.equals(modifyIndex, node1.modifyIndex);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, node, address, datacenter, taggedAddresses, meta, createIndex, modifyIndex);
		}
	}

	public static class Service {
		@JsonProperty("ID")
		private String id;

		@JsonProperty("Service")
		private String service;

		@JsonProperty("Tags")
		private List<String> tags;

		@JsonProperty("Address")
		private String address;

		@JsonProperty("Meta")
		private Map<String, String> meta;

		@JsonProperty("Port")
		private Integer port;

		@JsonProperty("EnableTagOverride")
		private Boolean enableTagOverride;

		@JsonProperty("CreateIndex")
		private Long createIndex;

		@JsonProperty("ModifyIndex")
		private Long modifyIndex;

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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Map<String, String> getMeta() {
			return meta;
		}

		public void setMeta(Map<String, String> meta) {
			this.meta = meta;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public Boolean getEnableTagOverride() {
			return enableTagOverride;
		}

		public void setEnableTagOverride(Boolean enableTagOverride) {
			this.enableTagOverride = enableTagOverride;
		}

		public Long getCreateIndex() {
			return createIndex;
		}

		public void setCreateIndex(Long createIndex) {
			this.createIndex = createIndex;
		}

		public Long getModifyIndex() {
			return modifyIndex;
		}

		public void setModifyIndex(Long modifyIndex) {
			this.modifyIndex = modifyIndex;
		}

		@Override
		public String toString() {
			return "Service{" + "id='" + id + '\'' + ", service='" + service + '\'' + ", tags=" + tags + ", address='"
					+ address + '\'' + ", meta=" + meta + ", port=" + port + ", enableTagOverride=" + enableTagOverride
					+ ", createIndex=" + createIndex + ", modifyIndex=" + modifyIndex + '}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Service service1 = (Service) o;
			return Objects.equals(id, service1.id) && Objects.equals(service, service1.service)
					&& Objects.equals(tags, service1.tags) && Objects.equals(address, service1.address)
					&& Objects.equals(meta, service1.meta) && Objects.equals(port, service1.port)
					&& Objects.equals(enableTagOverride, service1.enableTagOverride)
					&& Objects.equals(createIndex, service1.createIndex)
					&& Objects.equals(modifyIndex, service1.modifyIndex);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, service, tags, address, meta, port, enableTagOverride, createIndex, modifyIndex);
		}
	}

	@JsonProperty("Node")
	private Node node;

	@JsonProperty("Service")
	private Service service;

	@JsonProperty("Checks")
	private List<Check> checks;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Check> getChecks() {
		return checks;
	}

	public void setChecks(List<Check> checks) {
		this.checks = checks;
	}

	@Override
	public String toString() {
		return "HealthService{" + "node=" + node + ", service=" + service + ", checks=" + checks + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		HealthService that = (HealthService) o;
		return Objects.equals(node, that.node) && Objects.equals(service, that.service)
				&& Objects.equals(checks, that.checks);
	}

	@Override
	public int hashCode() {
		return Objects.hash(node, service, checks);
	}
}