package com.ecwid.consul.v1.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class CatalogDeregistration {
	@JsonProperty("Datacenter")
	private String datacenter;

	@JsonProperty("Node")
	private String node;

	@JsonProperty("CheckID")
	private String checkId;

	@JsonProperty("ServiceID")
	private String serviceId;

	@JsonProperty("WriteRequest")
	private WriteRequest writeRequest;

	public String getDatacenter() {
		return datacenter;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public WriteRequest getWriteRequest() {
		return writeRequest;
	}

	public void setWriteRequest(WriteRequest writeRequest) {
		this.writeRequest = writeRequest;
	}

	@Override
	public String toString() {
		return "CatalogDeregistration{" + "datacenter='" + datacenter + '\'' + ", node='" + node + '\'' + ", checkId='"
				+ checkId + '\'' + ", serviceId='" + serviceId + '\'' + ", writeRequest=" + writeRequest + '}';
	}
}