package com.ecwid.consul.v1.agent.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class Service {
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

	@JsonProperty("Meta")
	@SerializedName("Meta")
	private Map<String, String> meta;

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
		return "Service{" +
				"id='" + id + '\'' +
				", service='" + service + '\'' +
				", tags=" + tags +
				", address='" + address + '\'' +
				", meta=" + meta +
				", port=" + port +
				", enableTagOverride=" + enableTagOverride +
				", createIndex=" + createIndex +
				", modifyIndex=" + modifyIndex +
				'}';
	}
}