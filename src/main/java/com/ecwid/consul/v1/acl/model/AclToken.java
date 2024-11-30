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
public final class AclToken {
	@JsonProperty("AccessorID")
	private String accessorId;
	@JsonProperty("SecretID")
	private CharSequence secretId;
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
	@JsonProperty("ExpandedPolicies")
	private List<AclPolicy> expandedPolicies;
	@JsonProperty("ExpandedRoles")
	private List<AclRole> expandedRoles;
	@JsonProperty("AgentACLDefaultPolicy")
	private String agentACLDefaultPolicy;
	@JsonProperty("AgentACLDownPolicy")
	private String agentACLDownPolicy;
	@JsonProperty("ResolvedByAgent")
	private String resolvedByAgent;
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

	public final List<AclTemplatePolicy> getTemplatedPolicies() {
		return templatedPolicies;
	}

	public final void setTemplatedPolicies(List<AclTemplatePolicy> templatedPolicies) {
		this.templatedPolicies = templatedPolicies;
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

	public final List<AclPolicy> getExpandedPolicies() {
		return expandedPolicies;
	}

	public final void setExpandedPolicies(List<AclPolicy> expandedPolicies) {
		this.expandedPolicies = expandedPolicies;
	}

	public final List<AclRole> getExpandedRoles() {
		return expandedRoles;
	}

	public final void setExpandedRoles(List<AclRole> expandedRoles) {
		this.expandedRoles = expandedRoles;
	}

	public final String getAgentACLDefaultPolicy() {
		return agentACLDefaultPolicy;
	}

	public final void setAgentACLDefaultPolicy(String agentACLDefaultPolicy) {
		this.agentACLDefaultPolicy = agentACLDefaultPolicy;
	}

	public final String getAgentACLDownPolicy() {
		return agentACLDownPolicy;
	}

	public final void setAgentACLDownPolicy(String agentACLDownPolicy) {
		this.agentACLDownPolicy = agentACLDownPolicy;
	}

	public final String getResolvedByAgent() {
		return resolvedByAgent;
	}

	public final void setResolvedByAgent(String resolvedByAgent) {
		this.resolvedByAgent = resolvedByAgent;
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
	public int hashCode() {
		return Objects.hash(accessorId, agentACLDefaultPolicy, agentACLDownPolicy, createIndex, createTime, description,
				expandedPolicies, expandedRoles, hash, local, modifyIndex, policies, resolvedByAgent, secretId,
				templatedPolicies);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AclToken)) {
			return false;
		}
		AclToken other = (AclToken) obj;
		return Objects.equals(accessorId, other.accessorId)
				&& Objects.equals(agentACLDefaultPolicy, other.agentACLDefaultPolicy)
				&& Objects.equals(agentACLDownPolicy, other.agentACLDownPolicy) && createIndex == other.createIndex
				&& Objects.equals(createTime, other.createTime) && Objects.equals(description, other.description)
				&& Objects.equals(expandedPolicies, other.expandedPolicies)
				&& Objects.equals(expandedRoles, other.expandedRoles) && Objects.equals(hash, other.hash)
				&& local == other.local && modifyIndex == other.modifyIndex && Objects.equals(policies, other.policies)
				&& Objects.equals(resolvedByAgent, other.resolvedByAgent)
				&& Utils.charSequenceEquals(secretId, other.secretId)
				&& Objects.equals(templatedPolicies, other.templatedPolicies);
	}

	@Override
	public String toString() {
		return "AclToken [accessorId=" + accessorId + ", description=" + description + ", policies=" + policies
				+ ", templatedPolicies=" + templatedPolicies + ", local=" + local + ", createTime=" + createTime
				+ ", hash=" + hash + ", expandedPolicies=" + expandedPolicies + ", expandedRoles=" + expandedRoles
				+ ", agentACLDefaultPolicy=" + agentACLDefaultPolicy + ", agentACLDownPolicy=" + agentACLDownPolicy
				+ ", resolvedByAgent=" + resolvedByAgent + ", createIndex=" + createIndex + ", modifyIndex="
				+ modifyIndex + "]";
	}
}