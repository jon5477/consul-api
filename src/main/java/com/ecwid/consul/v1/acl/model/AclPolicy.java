package com.ecwid.consul.v1.acl.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jon Huang (jon5477)
 */
@JsonInclude(Include.NON_NULL)
public final class AclPolicy {
	@JsonProperty("ID")
	private String id;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("Rules")
	private String rules;
	@JsonProperty("Datacenters")
	private String datacenter;
	@JsonProperty("Hash")
	private String hash;
	@JsonProperty("CreateIndex")
	private Integer createIndex;
	@JsonProperty("ModifyIndex")
	private Integer modifyIndex;

	public AclPolicy() {
	}

	public AclPolicy(String id, String name) {
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

	public final String getRules() {
		return rules;
	}

	public final void setRules(String rules) {
		this.rules = rules;
	}

	public final String getDatacenter() {
		return datacenter;
	}

	public final void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
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
		return Objects.hash(createIndex, datacenter, description, hash, id, modifyIndex, name, rules);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AclPolicy)) {
			return false;
		}
		AclPolicy other = (AclPolicy) obj;
		return Objects.equals(createIndex, other.createIndex) && Objects.equals(datacenter, other.datacenter)
				&& Objects.equals(description, other.description) && Objects.equals(hash, other.hash)
				&& Objects.equals(id, other.id) && Objects.equals(modifyIndex, other.modifyIndex)
				&& Objects.equals(name, other.name) && Objects.equals(rules, other.rules);
	}

	@Override
	public final String toString() {
		return "AclPolicy [id=" + id + ", name=" + name + ", description=" + description + ", rules=" + rules
				+ ", datacenter=" + datacenter + ", hash=" + hash + ", createIndex=" + createIndex + ", modifyIndex="
				+ modifyIndex + "]";
	}
}