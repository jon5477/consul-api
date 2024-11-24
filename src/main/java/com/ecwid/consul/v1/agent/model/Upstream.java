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
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * 
 * @author Jon Huang
 *
 */
public class Upstream {
	@JsonProperty("destination_name")
	private String destinationName;
	@JsonProperty("destination_peer")
	private String destinationPeer;
	@JsonProperty("local_bind_port")
	private Integer localBindPort;
	@JsonProperty("local_bind_address")
	private String localBindAddress;
	/**
	 * This parameter conflicts with the local_bind_port or local_bind_address
	 * parameters. Supported when using Envoy as a proxy.
	 */
	@JsonProperty("local_bind_socket_path")
	private String localBindSocketPath;
	/**
	 * String value that specifies a Unix octal that configures file permissions for
	 * the socket.
	 */
	@JsonProperty("local_bind_socket_mode")
	private String localBindSocketMode;
	@JsonProperty("destination_type")
	private UpstreamDestinationType destinationType;
	@JsonProperty("datacenter")
	private String datacenter;
	@JsonProperty("config")
	private ObjectNode config;
	@JsonProperty("mesh_gateway")
	private ObjectNode meshGateway;

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationPeer() {
		return destinationPeer;
	}

	public void setDestinationPeer(String destinationPeer) {
		this.destinationPeer = destinationPeer;
	}

	public Integer getLocalBindPort() {
		return localBindPort;
	}

	public void setLocalBindPort(Integer localBindPort) {
		this.localBindPort = localBindPort;
	}

	public String getLocalBindAddress() {
		return localBindAddress;
	}

	public void setLocalBindAddress(String localBindAddress) {
		this.localBindAddress = localBindAddress;
	}

	public String getLocalBindSocketPath() {
		return localBindSocketPath;
	}

	public void setLocalBindSocketPath(String localBindSocketPath) {
		this.localBindSocketPath = localBindSocketPath;
	}

	public String getLocalBindSocketMode() {
		return localBindSocketMode;
	}

	public void setLocalBindSocketMode(String localBindSocketMode) {
		this.localBindSocketMode = localBindSocketMode;
	}

	public UpstreamDestinationType getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(UpstreamDestinationType destinationType) {
		this.destinationType = destinationType;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public ObjectNode getConfig() {
		return config;
	}

	public void setConfig(ObjectNode config) {
		this.config = config;
	}

	public ObjectNode getMeshGateway() {
		return meshGateway;
	}

	public void setMeshGateway(ObjectNode meshGateway) {
		this.meshGateway = meshGateway;
	}

	@Override
	public String toString() {
		return "Upstream [destinationName=" + destinationName + ", destinationPeer=" + destinationPeer
				+ ", localBindPort=" + localBindPort + ", localBindAddress=" + localBindAddress
				+ ", localBindSocketPath=" + localBindSocketPath + ", localBindSocketMode=" + localBindSocketMode
				+ ", destinationType=" + destinationType + ", datacenter=" + datacenter + ", config=" + config
				+ ", meshGateway=" + meshGateway + "]";
	}
}