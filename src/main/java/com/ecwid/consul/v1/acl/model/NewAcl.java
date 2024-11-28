package com.ecwid.consul.v1.acl.model;

import java.util.List;
import java.util.Objects;

import com.ecwid.consul.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jon Huang (jon5477)
 */
@JsonInclude(Include.NON_NULL)
public final class NewAcl {
	@JsonProperty("AccessorID")
	private String accessorId;
	@JsonProperty("SecretID")
	private CharSequence secretId;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("Policies")
	private List<AclPolicy> policies;
	@JsonProperty("Roles")
	private List<AclRole> roles;
	@JsonProperty("TemplatedPolicies")
	private List<AclTemplatePolicy> templatedPolicies;
	@JsonProperty("ServiceIdentities")
	private List<AclServiceIdentity> serviceIdentities;
	@JsonProperty("NodeIdentities")
	private List<AclNodeIdentity> nodeIdentities;
	@JsonProperty("Local")
	private boolean local;
	@JsonProperty("ExpirationTime")
	private String expirationTime;
	@JsonProperty("ExpirationTTL")
	private String expirationTtl;

	public final String getAccessorId() {
		return accessorId;
	}

	public final void setAccessorId(String accessorId) {
		this.accessorId = accessorId;
	}

	public final CharSequence getSecretId() {
		return secretId;
	}

	public final void setSecretId(CharSequence secretId) {
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

	public final List<AclTemplatePolicy> getTemplatedPolicies() {
		return templatedPolicies;
	}

	public final void setTemplatedPolicies(List<AclTemplatePolicy> templatedPolicies) {
		this.templatedPolicies = templatedPolicies;
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

	public final String getExpirationTtl() {
		return expirationTtl;
	}

	public final void setExpirationTtl(String expirationTtl) {
		this.expirationTtl = expirationTtl;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(accessorId, description, expirationTime, expirationTtl, local, nodeIdentities, policies,
				roles, secretId, serviceIdentities, templatedPolicies);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof NewAcl)) {
			return false;
		}
		NewAcl other = (NewAcl) obj;
		return Objects.equals(accessorId, other.accessorId) && Objects.equals(description, other.description)
				&& Objects.equals(expirationTime, other.expirationTime)
				&& Objects.equals(expirationTtl, other.expirationTtl) && local == other.local
				&& Objects.equals(nodeIdentities, other.nodeIdentities) && Objects.equals(policies, other.policies)
				&& Objects.equals(roles, other.roles) && Utils.charSequenceEquals(secretId, other.secretId)
				&& Objects.equals(serviceIdentities, other.serviceIdentities)
				&& Objects.equals(templatedPolicies, other.templatedPolicies);
	}

	@Override
	public final String toString() {
		return "NewAcl [accessorId=" + accessorId + ", description=" + description + ", policies=" + policies
				+ ", roles=" + roles + ", templatedPolicies=" + templatedPolicies + ", serviceIdentities="
				+ serviceIdentities + ", nodeIdentities=" + nodeIdentities + ", local=" + local + ", expirationTime="
				+ expirationTime + ", expirationTtl=" + expirationTtl + "]";
	}
}