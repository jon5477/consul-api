package com.ecwid.consul.v1.health.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class Check {
	public enum CheckStatus {
		@JsonProperty("unknown")
		UNKNOWN, @JsonProperty("passing")
		PASSING, @JsonProperty("warning")
		WARNING, @JsonProperty("critical")
		CRITICAL
	}

	@JsonProperty("Node")
	private String node;

	@JsonProperty("CheckID")
	private String checkId;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("Status")
	private CheckStatus status;

	@JsonProperty("Notes")
	private String notes;

	@JsonProperty("Output")
	private String output;

	@JsonProperty("ServiceID")
	private String serviceId;

	@JsonProperty("ServiceName")
	private String serviceName;

	@JsonProperty("ServiceTags")
	private List<String> serviceTags;

	@JsonProperty("CreateIndex")
	private Long createIndex;

	@JsonProperty("ModifyIndex")
	private Long modifyIndex;

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CheckStatus getStatus() {
		return status;
	}

	public void setStatus(CheckStatus status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<String> getServiceTags() {
		return serviceTags;
	}

	public void setServiceTags(List<String> serviceTags) {
		this.serviceTags = serviceTags;
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
		return Objects.hash(checkId, createIndex, modifyIndex, name, node, notes, output, serviceId, serviceName,
				serviceTags, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Check)) {
			return false;
		}
		Check other = (Check) obj;
		return Objects.equals(checkId, other.checkId) && Objects.equals(createIndex, other.createIndex)
				&& Objects.equals(modifyIndex, other.modifyIndex) && Objects.equals(name, other.name)
				&& Objects.equals(node, other.node) && Objects.equals(notes, other.notes)
				&& Objects.equals(output, other.output) && Objects.equals(serviceId, other.serviceId)
				&& Objects.equals(serviceName, other.serviceName) && Objects.equals(serviceTags, other.serviceTags)
				&& status == other.status;
	}

	@Override
	public String toString() {
		return "Check{" + "node='" + node + '\'' + ", checkId='" + checkId + '\'' + ", name='" + name + '\''
				+ ", status=" + status + ", notes='" + notes + '\'' + ", output='" + output + '\'' + ", serviceId='"
				+ serviceId + '\'' + ", serviceName='" + serviceName + '\'' + ", serviceTags=" + serviceTags
				+ ", createIndex=" + createIndex + ", modifyIndex=" + modifyIndex + '}';
	}
}