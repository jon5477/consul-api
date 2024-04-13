package com.ecwid.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jon Huang
 */
public enum ProxyMode {
	@JsonProperty("transparent")
	TRANSPARENT,
	@JsonProperty("direct")
	DIRECT;
}