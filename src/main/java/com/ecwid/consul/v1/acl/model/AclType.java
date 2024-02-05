package com.ecwid.consul.v1.acl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public enum AclType {
	@JsonProperty("client")
	@SerializedName("client")
	CLIENT,

	@JsonProperty("management")
	@SerializedName("management")
	MANAGEMENT
}