package com.ecwid.consul.v1.acl.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public final class AclServiceIdentity {
	@JsonProperty("ServiceName")
	@SerializedName("ServiceName")
	private String serviceName;
	@JsonProperty("Datacenters")
	@SerializedName("Datacenters")
	private List<String> datacenters;

	public AclServiceIdentity() {
	}

	public AclServiceIdentity(String serviceName, List<String> datacenters) {
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
		if (!(obj instanceof AclServiceIdentity)) {
			return false;
		}
		AclServiceIdentity other = (AclServiceIdentity) obj;
		return Objects.equals(datacenters, other.datacenters) && Objects.equals(serviceName, other.serviceName);
	}

	@Override
	public final String toString() {
		return "AclServiceIdentity [serviceName=" + serviceName + ", datacenters=" + datacenters + "]";
	}
}