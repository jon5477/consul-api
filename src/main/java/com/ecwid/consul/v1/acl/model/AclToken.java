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
public final class AclToken {
	@JsonProperty("AccessorID")
	private String accessorId;
	@JsonProperty("SecretID")
	private String secretId;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("Policies")
	private List<AclPolicy> policies;
	@JsonProperty("TemplatedPolicies")
	private List<AclTemplatePolicy> templatedPolicies;
	@JsonProperty("Local")
	private boolean local;
	@JsonProperty("CreateTime")
	private String createTime;
	@JsonProperty("Hash")
	private String hash;
//	@JsonProperty("ExpandedPolicies")
//	private List<AclPolicy> expandedPolicies;
//	@JsonProperty("ExpandedRoles")
//	private List<AclRole> expandedRoles;
//	@JsonProperty("AgentACLDefaultPolicy")
//	private String agentACLDefaultPolicy;
//	@JsonProperty("AgentACLDownPolicy")
//	private String agentACLDownPolicy;
//	@JsonProperty("ResolvedByAgent")
//	private String resolvedByAgent;
	@JsonProperty("CreateIndex")
	private int createIndex;
	@JsonProperty("ModifyIndex")
	private int modifyIndex;

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

	public final boolean isLocal() {
		return local;
	}

	public final void setLocal(boolean local) {
		this.local = local;
	}

	public final String getCreateTime() {
		return createTime;
	}

	public final void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public final String getHash() {
		return hash;
	}

	public final void setHash(String hash) {
		this.hash = hash;
	}

	public final int getCreateIndex() {
		return createIndex;
	}

	public final void setCreateIndex(int createIndex) {
		this.createIndex = createIndex;
	}

	public final int getModifyIndex() {
		return modifyIndex;
	}

	public final void setModifyIndex(int modifyIndex) {
		this.modifyIndex = modifyIndex;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(accessorId, createIndex, createTime, description, hash, local, modifyIndex, policies,
				secretId, templatedPolicies);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AclToken)) {
			return false;
		}
		AclToken other = (AclToken) obj;
		return Objects.equals(accessorId, other.accessorId) && createIndex == other.createIndex
				&& Objects.equals(createTime, other.createTime) && Objects.equals(description, other.description)
				&& Objects.equals(hash, other.hash) && local == other.local && modifyIndex == other.modifyIndex
				&& Objects.equals(policies, other.policies) && Objects.equals(secretId, other.secretId)
				&& Objects.equals(templatedPolicies, other.templatedPolicies);
	}

	@Override
	public final String toString() {
		return "AclToken [accessorId=" + accessorId + ", secretId=" + secretId + ", description=" + description
				+ ", policies=" + policies + ", templatedPolicies=" + templatedPolicies + ", local=" + local
				+ ", createTime=" + createTime + ", hash=" + hash + ", createIndex=" + createIndex + ", modifyIndex="
				+ modifyIndex + "]";
	}
}