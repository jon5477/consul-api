package com.ecwid.consul.v1.acl.model.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public enum LegacyAclType {
	@JsonProperty("client")
	CLIENT,

	@JsonProperty("management")
	MANAGEMENT
}