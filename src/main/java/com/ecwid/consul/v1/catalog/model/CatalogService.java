package com.ecwid.consul.v1.catalog.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class CatalogService {
	@JsonProperty("ID")
	private String id;

	@JsonProperty("Node")
	private String node;

	@JsonProperty("Address")
	private String address;

	@JsonProperty("Datacenter")
	private String datacenter;

	@JsonProperty("TaggedAddresses")
	private Map<String, String> taggedAddresses;

	@JsonProperty("NodeMeta")
	private Map<String, String> nodeMeta;

	@JsonProperty("ServiceID")
	private String serviceId;

	@JsonProperty("ServiceName")
	private String serviceName;

	@JsonProperty("ServiceTags")
	private List<String> serviceTags;

	@JsonProperty("ServiceAddress")
	private String serviceAddress;

	@JsonProperty("ServiceMeta")
	private Map<String, String> serviceMeta;

	@JsonProperty("ServicePort")
	private Integer servicePort;

	@JsonProperty("ServiceEnableTagOverride")
	private Boolean serviceEnableTagOverride;

	@JsonProperty("CreateIndex")
	private Long createIndex;

	@JsonProperty("ModifyIndex")
	private Long modifyIndex;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public Map<String, String> getTaggedAddresses() {
		return taggedAddresses;
	}

	public void setTaggedAddresses(Map<String, String> taggedAddresses) {
		this.taggedAddresses = taggedAddresses;
	}

	public Map<String, String> getNodeMeta() {
		return nodeMeta;
	}

	public void setNodeMeta(Map<String, String> nodeMeta) {
		this.nodeMeta = nodeMeta;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<String> getServiceTags() {
		return serviceTags;
	}

	public void setServiceTags(List<String> serviceTags) {
		this.serviceTags = serviceTags;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	public Map<String, String> getServiceMeta() {
		return serviceMeta;
	}

	public void setServiceMeta(Map<String, String> serviceMeta) {
		this.serviceMeta = serviceMeta;
	}

	public Integer getServicePort() {
		return servicePort;
	}

	public void setServicePort(Integer servicePort) {
		this.servicePort = servicePort;
	}

	public Boolean getServiceEnableTagOverride() {
		return serviceEnableTagOverride;
	}

	public void setServiceEnableTagOverride(Boolean serviceEnableTagOverride) {
		this.serviceEnableTagOverride = serviceEnableTagOverride;
	}

	public Long getCreateIndex() {
		return createIndex;
	}

	public void setCreateIndex(Long createIndex) {
		this.createIndex = createIndex;
	}

	public Long getModifyIndex() {
		return modifyIndex;
	}

	public void setModifyIndex(Long modifyIndex) {
		this.modifyIndex = modifyIndex;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, createIndex, datacenter, id, modifyIndex, node, nodeMeta, serviceAddress,
				serviceEnableTagOverride, serviceId, serviceMeta, serviceName, servicePort, serviceTags,
				taggedAddresses);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CatalogService)) {
			return false;
		}
		CatalogService other = (CatalogService) obj;
		return Objects.equals(address, other.address) && Objects.equals(createIndex, other.createIndex)
				&& Objects.equals(datacenter, other.datacenter) && Objects.equals(id, other.id)
				&& Objects.equals(modifyIndex, other.modifyIndex) && Objects.equals(node, other.node)
				&& Objects.equals(nodeMeta, other.nodeMeta) && Objects.equals(serviceAddress, other.serviceAddress)
				&& Objects.equals(serviceEnableTagOverride, other.serviceEnableTagOverride)
				&& Objects.equals(serviceId, other.serviceId) && Objects.equals(serviceMeta, other.serviceMeta)
				&& Objects.equals(serviceName, other.serviceName) && Objects.equals(servicePort, other.servicePort)
				&& Objects.equals(serviceTags, other.serviceTags)
				&& Objects.equals(taggedAddresses, other.taggedAddresses);
	}

	@Override
	public String toString() {
		return "CatalogService [id=" + id + ", node=" + node + ", address=" + address + ", datacenter=" + datacenter
				+ ", taggedAddresses=" + taggedAddresses + ", nodeMeta=" + nodeMeta + ", serviceId=" + serviceId
				+ ", serviceName=" + serviceName + ", serviceTags=" + serviceTags + ", serviceAddress=" + serviceAddress
				+ ", serviceMeta=" + serviceMeta + ", servicePort=" + servicePort + ", serviceEnableTagOverride="
				+ serviceEnableTagOverride + ", createIndex=" + createIndex + ", modifyIndex=" + modifyIndex + "]";
	}
}