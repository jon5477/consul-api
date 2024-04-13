package com.ecwid.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jon Huang
 */
public enum CheckStatus {
	/**
	 * Indicates the service is passing.
	 */
	@JsonProperty("passing")
	PASSING,
	/**
	 * Indicates the service has a warning.
	 */
	@JsonProperty("warning")
	WARNING,
	/**
	 * Indicates the service is critical.
	 */
	@JsonProperty("critical")
	CRITICAL;
}