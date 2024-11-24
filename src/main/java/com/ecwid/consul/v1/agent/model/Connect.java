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