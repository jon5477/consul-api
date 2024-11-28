package com.ecwid.consul.v1.acl.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jon Huang (jon5477)
 */
@JsonInclude(Include.NON_NULL)
public final class AclRole {
	@JsonProperty("ID")
	private String id;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("Policies")
	private List<AclPolicy> policies;
	@JsonProperty("ServiceIdentities")
	private List<ServiceIdentity> serviceIdentities;
	@JsonProperty("Hash")
	private String hash;
	@JsonProperty("CreateIndex")
	private Integer createIndex;
	@JsonProperty("ModifyIndex")
	private Integer modifyIndex;

	public AclRole() {
	}

	public AclRole(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final List<AclPolicy> getPolicies() {
		return policies;
	}

	public final void setPolicies(List<AclPolicy> policies) {
		this.policies = policies;
	}

	public final List<ServiceIdentity> getServiceIdentities() {
		return serviceIdentities;
	}

	public final void setServiceIdentities(List<ServiceIdentity> serviceIdentities) {
		this.serviceIdentities = serviceIdentities;
	}

	public final String getHash() {
		return hash;
	}

	public final void setHash(String hash) {
		this.hash = hash;
	}

	public final Integer getCreateIndex() {
		return createIndex;
	}

	public final void setCreateIndex(Integer createIndex) {
		this.createIndex = createIndex;
	}

	public final Integer getModifyIndex() {
		return modifyIndex;
	}

	public final void setModifyIndex(Integer modifyIndex) {
		this.modifyIndex = modifyIndex;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(createIndex, description, hash, id, modifyIndex, name, policies, serviceIdentities);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AclRole)) {
			return false;
		}
		AclRole other = (AclRole) obj;
		return Objects.equals(createIndex, other.createIndex) && Objects.equals(description, other.description)
				&& Objects.equals(hash, other.hash) && Objects.equals(id, other.id)
				&& Objects.equals(modifyIndex, other.modifyIndex) && Objects.equals(name, other.name)
				&& Objects.equals(policies, other.policies)
				&& Objects.equals(serviceIdentities, other.serviceIdentities);
	}

	@Override
	public final String toString() {
		return "AclRole [id=" + id + ", name=" + name + ", description=" + description + ", policies=" + policies
				+ ", serviceIdentities=" + serviceIdentities + ", hash=" + hash + ", createIndex=" + createIndex
				+ ", modifyIndex=" + modifyIndex + "]";
	}
}