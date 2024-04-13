package com.ecwid.consul.v1.agent.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author Jon Huang
 */
public class ProxyFields {
	@JsonProperty("destination_service_name")
	private String destinationServiceName;

	@JsonProperty("destination_service_id")
	private String destinationServiceId;

	@JsonProperty("local_service_address")
	private String localServiceAddress;

	@JsonProperty("local_service_port")
	private int localServicePort;

	@JsonProperty("local_service_socket_path")
	private String localServiceSocketPath;

	@JsonProperty("mode")
	private ProxyMode mode;

	@JsonProperty("transparent_proxy")
	private ObjectNode transparentProxy;

	@JsonProperty("config")
	private ObjectNode config;

	@JsonProperty("upstreams")
	private List<Upstream> upstreams;

	@JsonProperty("mesh_gateway")
	private ObjectNode meshGateway;

	@JsonProperty("expose")
	private ObjectNode expose;

	public String getDestinationServiceName() {
		return destinationServiceName;
	}

	public void setDestinationServiceName(String destinationServiceName) {
		this.destinationServiceName = destinationServiceName;
	}

	public String getDestinationServiceId() {
		return destinationServiceId;
	}

	public void setDestinationServiceId(String destinationServiceId) {
		this.destinationServiceId = destinationServiceId;
	}

	public String getLocalServiceAddress() {
		return localServiceAddress;
	}

	public void setLocalServiceAddress(String localServiceAddress) {
		this.localServiceAddress = localServiceAddress;
	}

	public int getLocalServicePort() {
		return localServicePort;
	}

	public void setLocalServicePort(int localServicePort) {
		this.localServicePort = localServicePort;
	}

	public ObjectNode getConfig() {
		return config;
	}

	public void setConfig(ObjectNode config) {
		this.config = config;
	}

	public List<Upstream> getUpstreams() {
		return upstreams;
	}

	public void setUpstreams(List<Upstream> upstreams) {
		this.upstreams = upstreams;
	}

	public ObjectNode getMeshGateway() {
		return meshGateway;
	}

	public void setMeshGateway(ObjectNode meshGateway) {
		this.meshGateway = meshGateway;
	}

	public ObjectNode getExpose() {
		return expose;
	}

	public void setExpose(ObjectNode expose) {
		this.expose = expose;
	}

	@Override
	public String toString() {
		return "ProxyFields [destinationServiceName=" + destinationServiceName + ", destinationServiceId="
				+ destinationServiceId + ", localServiceAddress=" + localServiceAddress + ", localServicePort="
				+ localServicePort + ", config=" + config + ", upstreams=" + upstreams + ", meshGateway=" + meshGateway
				+ ", expose=" + expose + "]";
	}
}