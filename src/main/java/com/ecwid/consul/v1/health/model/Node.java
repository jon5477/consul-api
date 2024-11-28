package com.ecwid.consul.v1.health.model;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Node {
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
	public int hashCode() {
		return Objects.hash(address, createIndex, datacenter, id, meta, modifyIndex, node, taggedAddresses);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		Node other = (Node) obj;
		return Objects.equals(address, other.address) && Objects.equals(createIndex, other.createIndex)
				&& Objects.equals(datacenter, other.datacenter) && Objects.equals(id, other.id)
				&& Objects.equals(meta, other.meta) && Objects.equals(modifyIndex, other.modifyIndex)
				&& Objects.equals(node, other.node) && Objects.equals(taggedAddresses, other.taggedAddresses);
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", node=" + node + ", address=" + address + ", datacenter=" + datacenter
				+ ", taggedAddresses=" + taggedAddresses + ", meta=" + meta + ", createIndex=" + createIndex
				+ ", modifyIndex=" + modifyIndex + "]";
	}
}