package com.ecwid.consul.v1.acl.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public final class UpdateAcl {
	@JsonProperty("AccessorID")
	@SerializedName("AccessorID")
	private String accessorId;
	@JsonProperty("SecretID")
	@SerializedName("SecretID")
	private String secretId;
	@JsonProperty("Description")
	@SerializedName("Description")
	private String description;
	@JsonProperty("Policies")
	@SerializedName("Policies")
	private List<AclPolicy> policies;
	@JsonProperty("Roles")
	@SerializedName("Roles")
	private List<AclRole> roles;
	@JsonProperty("ServiceIdentities")
	@SerializedName("ServiceIdentities")
	private List<AclServiceIdentity> serviceIdentities;
	@JsonProperty("NodeIdentities")
	@SerializedName("NodeIdentities")
	private List<AclNodeIdentity> nodeIdentities;
	@JsonProperty("Local")
	@SerializedName("Local")
	private boolean local;
	@JsonProperty("ExpirationTime")
	@SerializedName("ExpirationTime")
	private String expirationTime;
	@JsonProperty("Namespace")
	@SerializedName("Namespace")
	private String namespace;

	public final String getAccessorId() {
		return accessorId;
	}

	public final void setAccessorId(String accessorId) {
		this.accessorId = accessorId;
	}

	public final String getSecretId() {
		return secretId;
	}

	public final void setSecretId(String secretId) {
		this.secretId = secretId;
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

	public final List<AclRole> getRoles() {
		return roles;
	}

	public final void setRoles(List<AclRole> roles) {
		this.roles = roles;
	}

	public final List<AclServiceIdentity> getServiceIdentities() {
		return serviceIdentities;
	}

	public final void setServiceIdentities(List<AclServiceIdentity> serviceIdentities) {
		this.serviceIdentities = serviceIdentities;
	}

	public final List<AclNodeIdentity> getNodeIdentities() {
		return nodeIdentities;
	}

	public final void setNodeIdentities(List<AclNodeIdentity> nodeIdentities) {
		this.nodeIdentities = nodeIdentities;
	}

	public final boolean isLocal() {
		return local;
	}

	public final void setLocal(boolean local) {
		this.local = local;
	}

	public final String getExpirationTime() {
		return expirationTime;
	}

	public final void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public final String getNamespace() {
		return namespace;
	}

	public final void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(accessorId, description, expirationTime, local, namespace, nodeIdentities, policies, roles,
				secretId, serviceIdentities);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UpdateAcl)) {
			return false;
		}
		UpdateAcl other = (UpdateAcl) obj;
		return Objects.equals(accessorId, other.accessorId) && Objects.equals(description, other.description)
				&& Objects.equals(expirationTime, other.expirationTime) && local == other.local
				&& Objects.equals(namespace, other.namespace) && Objects.equals(nodeIdentities, other.nodeIdentities)
				&& Objects.equals(policies, other.policies) && Objects.equals(roles, other.roles)
				&& Objects.equals(secretId, other.secretId)
				&& Objects.equals(serviceIdentities, other.serviceIdentities);
	}

	@Override
	public final String toString() {
		return "UpdateAcl [accessorId=" + accessorId + ", secretId=" + secretId + ", description=" + description
				+ ", policies=" + policies + ", roles=" + roles + ", serviceIdentities=" + serviceIdentities
				+ ", nodeIdentities=" + nodeIdentities + ", local=" + local + ", expirationTime=" + expirationTime
				+ ", namespace=" + namespace + "]";
	}
}