package com.ecwid.consul.v1.acl.model.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public enum LegacyAclType {
	@JsonProperty("client")
	@SerializedName("client")
	CLIENT,

	@JsonProperty("management")
	@SerializedName("management")
	MANAGEMENT
}