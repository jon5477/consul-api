package com.ecwid.consul.v1.acl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class UpdateAcl {
	@JsonProperty("ID")
	@SerializedName("ID")
	private String id;

	@JsonProperty("Name")
	@SerializedName("Name")
	private String name;

	@JsonProperty("Type")
	@SerializedName("Type")
	private AclType type;

	@JsonProperty("Rules")
	@SerializedName("Rules")
	private String rules;

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

	public AclType getType() {
		return type;
	}

	public void setType(AclType type) {
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
		return "UpdateAcl{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", type=" + type + ", rules='" + rules
				+ '\'' + '}';
	}
}