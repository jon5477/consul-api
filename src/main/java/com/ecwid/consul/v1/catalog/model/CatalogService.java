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
	public String toString() {
		return "CatalogService{" + "id='" + id + '\'' + ", node='" + node + '\'' + ", address='" + address + '\''
				+ ", datacenter='" + datacenter + '\'' + ", taggedAddresses=" + taggedAddresses + ", nodeMeta="
				+ nodeMeta + ", serviceId='" + serviceId + '\'' + ", serviceName='" + serviceName + '\''
				+ ", serviceTags=" + serviceTags + ", serviceAddress='" + serviceAddress + '\'' + ", serviceMeta="
				+ serviceMeta + ", servicePort=" + servicePort + ", serviceEnableTagOverride="
				+ serviceEnableTagOverride + ", createIndex=" + createIndex + ", modifyIndex=" + modifyIndex + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CatalogService that = (CatalogService) o;
		return Objects.equals(id, that.id) && Objects.equals(node, that.node) && Objects.equals(address, that.address)
				&& Objects.equals(datacenter, that.datacenter) && Objects.equals(taggedAddresses, that.taggedAddresses)
				&& Objects.equals(nodeMeta, that.nodeMeta) && Objects.equals(serviceId, that.serviceId)
				&& Objects.equals(serviceName, that.serviceName) && Objects.equals(serviceTags, that.serviceTags)
				&& Objects.equals(serviceAddress, that.serviceAddress) && Objects.equals(serviceMeta, that.serviceMeta)
				&& Objects.equals(servicePort, that.servicePort)
				&& Objects.equals(serviceEnableTagOverride, that.serviceEnableTagOverride)
				&& Objects.equals(createIndex, that.createIndex) && Objects.equals(modifyIndex, that.modifyIndex);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, node, address, datacenter, taggedAddresses, nodeMeta, serviceId, serviceName,
				serviceTags, serviceAddress, serviceMeta, servicePort, serviceEnableTagOverride, createIndex,
				modifyIndex);
	}
}