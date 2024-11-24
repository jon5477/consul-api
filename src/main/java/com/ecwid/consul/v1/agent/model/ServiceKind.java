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
public enum ServiceKind {
	/**
	 * The default consul service ("") which is a typical Consul service.
	 */
	@JsonProperty("")
	DEFAULT,
	/**
	 * <a href=
	 * "https://developer.hashicorp.com/consul/api-docs/agent/service#connect-proxy">Connect</a>
	 * proxies representing another service
	 */
	@JsonProperty("connect-proxy")
	CONNECT_PROXY,
	/**
	 * Instances of a <a href=
	 * "https://developer.hashicorp.com/consul/api-docs/agent/service#mesh-gateway">mesh
	 * gateway</a>
	 */
	@JsonProperty("mesh-gateway")
	MESH_GATEWAY,
	/**
	 * Instances of a <a href=
	 * "https://developer.hashicorp.com/consul/api-docs/agent/service#terminating-gateway">terminating
	 * gateway</a>
	 */
	@JsonProperty("terminating-gateway")
	TERMINATING_GATEWAY,
	/**
	 * Instances of a <a href=
	 * "https://developer.hashicorp.com/consul/api-docs/agent/service#ingress-gateway">ingress
	 * gateway</a>
	 */
	@JsonProperty("ingress-gateway")
	INGRESS_GATEWAY;
}
