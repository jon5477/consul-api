/*********************************************************************
* Copyright (c) 2024 Jon Huang
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package com.ecwid.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * 
 * @author Jon Huang
 *
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