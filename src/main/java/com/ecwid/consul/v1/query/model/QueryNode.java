package com.ecwid.consul.v1.query.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class QueryNode {
	public static class Node {
		@JsonProperty("ID")
		@SerializedName("ID")
		private String id;

		@JsonProperty("Node")
		@SerializedName("Node")
		private String node;

		@JsonProperty("Address")
		@SerializedName("Address")
		private String address;

		@JsonProperty("Datacenter")
		@SerializedName("Datacenter")
		private String datacenter;

		@JsonProperty("TaggedAddresses")
		@SerializedName("TaggedAddresses")
		private Map<String, String> taggedAddresses;

		@JsonProperty("Meta")
		@SerializedName("Meta")
		private Map<String, String> meta;

		@JsonProperty("CreateIndex")
		@SerializedName("CreateIndex")
		private Long createIndex;

		@JsonProperty("ModifyIndex")
		@SerializedName("ModifyIndex")
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
			return "Node{" +
				"id='" + id + '\'' +
				", node='" + node + '\'' +
				", address='" + address + '\'' +
				", datacenter='" + datacenter + '\'' +
				", taggedAddresses=" + taggedAddresses +
				", meta=" + meta +
				", createIndex=" + createIndex +
				", modifyIndex=" + modifyIndex +
				'}';
		}
	}

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

		@JsonProperty("Address")
		@SerializedName("Address")
		private String address;

		@JsonProperty("Port")
		@SerializedName("Port")
		private Integer port;

		@JsonProperty("EnableTagOverride")
		@SerializedName("EnableTagOverride")
		private Boolean enableTagOverride;

		@JsonProperty("CreateIndex")
		@SerializedName("CreateIndex")
		private Long createIndex;

		@JsonProperty("ModifyIndex")
		@SerializedName("ModifyIndex")
		private Long modifyIndex;

		@JsonProperty("Meta")
		@SerializedName("Meta")
		private Map<String, String> meta;

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
			return "Service{" +
				"id='" + id + '\'' +
				", service='" + service + '\'' +
				", tags=" + tags +
				", address='" + address + '\'' +
				", port=" + port +
				", enableTagOverride=" + enableTagOverride +
				", meta=" + meta +
				", createIndex=" + createIndex +
				", modifyIndex=" + modifyIndex +
				'}';
		}
	}

	@JsonProperty("Node")
	@SerializedName("Node")
	private Node node;

	@JsonProperty("Service")
	@SerializedName("Service")
	private Service service;

	@JsonProperty("Checks")
	@SerializedName("Checks")
	private List<Check> checks;

	public Node getNode() { return node; }

	public void setNode(Node node) { this.node = node; }

	public Service getService() { return service; }

	public void setService(Service service) { this.service = service; }

	public List<Check> getChecks() { return checks; }

	public void setChecks(List<Check> checks) { this.checks = checks; }

	@Override
	public String toString() {
		return "QueryNode{" +
			"node=" + node +
			", service=" + service +
			", checks=" + checks +
			'}';
	}
}