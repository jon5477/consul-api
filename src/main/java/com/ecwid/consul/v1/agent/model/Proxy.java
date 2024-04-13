package com.ecwid.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jon Huang
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