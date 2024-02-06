package com.ecwid.consul.v1.acl.model.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class LegacyNewAcl {
	@JsonProperty("Name")
	@SerializedName("Name")
	private String name;

	@JsonProperty("Type")
	@SerializedName("Type")
	private LegacyAclType type;

	@JsonProperty("Rules")
	@SerializedName("Rules")
	private String rules;

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
		return "LegacyNewAcl{" +
				"name='" + name + '\'' +
				", type=" + type +
				", rules='" + rules + '\'' +
				'}';
	}
}