package com.ecwid.consul.v1.acl.model.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class LegacyAcl {
	@JsonProperty("CreateIndex")
	private long createIndex;

	@JsonProperty("ModifyIndex")
	private long modifyIndex;

	@JsonProperty("ID")
	private String id;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("Type")
	private LegacyAclType type;

	@JsonProperty("Rules")
	private String rules;

	public long getCreateIndex() {
		return createIndex;
	}

	public void setCreateIndex(long createIndex) {
		this.createIndex = createIndex;
	}

	public long getModifyIndex() {
		return modifyIndex;
	}

	public void setModifyIndex(long modifyIndex) {
		this.modifyIndex = modifyIndex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LegacyAclType getType() {
		return type;
	}

	public void setType(LegacyAclType type) {
		this.type = type;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	@Override
	public String toString() {
		return "Acl{" +
				"createIndex=" + createIndex +
				", modifyIndex=" + modifyIndex +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", type=" + type +
				", rules='" + rules + '\'' +
				'}';
	}
}
