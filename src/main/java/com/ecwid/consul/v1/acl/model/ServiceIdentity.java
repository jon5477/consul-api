package com.ecwid.consul.v1.acl.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * 
 * @author Jon Huang
 *
 */
public final class ServiceIdentity {
	@JsonProperty("ServiceName")
	private String serviceName;
	@JsonProperty("Datacenters")
	private List<String> datacenters;

	public ServiceIdentity() {
	}

	public ServiceIdentity(String serviceName, List<String> datacenters) {
		this.serviceName = serviceName;
		this.datacenters = datacenters;
	}

	public final String getServiceName() {
		return serviceName;
	}

	public final void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public final List<String> getDatacenters() {
		return datacenters;
	}

	public final void setDatacenters(List<String> datacenters) {
		this.datacenters = datacenters;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(datacenters, serviceName);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ServiceIdentity)) {
			return false;
		}
		ServiceIdentity other = (ServiceIdentity) obj;
		return Objects.equals(datacenters, other.datacenters) && Objects.equals(serviceName, other.serviceName);
	}

	@Override
	public final String toString() {
		return "ServiceIdentity [serviceName=" + serviceName + ", datacenters=" + datacenters + "]";
	}
}