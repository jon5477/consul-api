package com.ecwid.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jon Huang
 */
public enum UpstreamDestinationType {
	/**
	 * Queries for upstream service types.
	 */
	@JsonProperty("service")
	SERVICE,
	/**
	 * Queries for upstream prepared queries.
	 */
	@JsonProperty("prepared_query")
	PREPARED_QUERY;
}