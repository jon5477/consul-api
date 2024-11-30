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
public class Proxy {
	@JsonProperty("name")
	private String name;

	@JsonProperty("kind")
	private String kind = "connect-proxy";

	@JsonProperty("proxy")
	private ProxyFields fields;

	@JsonProperty("port")
	private Integer port;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProxyFields getFields() {
		return fields;
	}

	public void setFields(ProxyFields fields) {
		this.fields = fields;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "Proxy [name=" + name + ", fields=" + fields + ", port=" + port + "]";
	}
}