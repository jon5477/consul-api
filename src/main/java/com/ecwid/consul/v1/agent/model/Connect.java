package com.ecwid.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Connect {
	@JsonProperty("Native")
	private Boolean nativeSupport;
	@JsonProperty("SidecarService")
	private ObjectNode sidecarService;

	public Boolean getNativeSupport() {
		return nativeSupport;
	}

	public void setNativeSupport(Boolean nativeSupport) {
		this.nativeSupport = nativeSupport;
	}

	public ObjectNode getSidecarService() {
		return sidecarService;
	}

	public void setSidecarService(ObjectNode sidecarService) {
		this.sidecarService = sidecarService;
	}

	@Override
	public String toString() {
		return "Connect [nativeSupport=" + nativeSupport + ", sidecarService=" + sidecarService + "]";
	}
}