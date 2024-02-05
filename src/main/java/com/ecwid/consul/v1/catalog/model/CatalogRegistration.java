package com.ecwid.consul.v1.catalog.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class CatalogRegistration {
	public static class Service {
		@JsonProperty("ID")
		@SerializedName("ID")
		private String id;

		@JsonProperty("Service")
		@SerializedName("Service")
		private String service;

		@JsonProperty("Tags")
		@SerializedName("Tags")
		private List<String> tags;

		@JsonProperty("Address")
		@SerializedName("Address")
		private String address;

		@JsonProperty("Meta")
		@SerializedName("Meta")
		private Map<String, String> meta;

		@JsonProperty("Port")
		@SerializedName("Port")
		private Integer port;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getService() {
			return service;
		}

		public void setService(String service) {
			this.service = service;
		}

		public List<String> getTags() {
			return tags;
		}

		public void setTags(List<String> tags) {
			this.tags = tags;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Map<String, String> getMeta() {
			return meta;
		}

		public void setMeta(Map<String, String> meta) {
			this.meta = meta;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		@Override
		public String toString() {
			return "Service{" + "id='" + id + '\'' + ", service='" + service + '\'' + ", tags=" + tags + ", address='"
					+ address + '\'' + ", meta=" + meta + ", port=" + port + '}';
		}
	}

	public static enum CheckStatus {
		@JsonProperty("unknown")
		@SerializedName("unknown")
		UNKNOWN, 
		@JsonProperty("passing")@SerializedName("passing")
		PASSING, 
		@JsonProperty("warning")@SerializedName("warning")
		WARNING, 
		@JsonProperty("critical")@SerializedName("critical")
		CRITICAL
	}

	public static class Check {
		@JsonProperty("Node")
		@SerializedName("Node")
		private String node;

		@JsonProperty("CheckID")
		@SerializedName("CheckID")
		private String checkId;

		@JsonProperty("Name")
		@SerializedName("Name")
		private String name;

		@JsonProperty("Notes")
		@SerializedName("Notes")
		private String notes;

		@JsonProperty("Status")
		@SerializedName("Status")
		private CheckStatus status;

		@JsonProperty("ServiceID")
		@SerializedName("ServiceID")
		private String serviceId;

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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public CheckStatus getStatus() {
			return status;
		}

		public void setStatus(CheckStatus status) {
			this.status = status;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}

		@Override
		public String toString() {
			return "Check{" + "node='" + node + '\'' + ", checkId='" + checkId + '\'' + ", name='" + name + '\''
					+ ", notes='" + notes + '\'' + ", status=" + status + ", serviceId='" + serviceId + '\'' + '}';
		}
	}

	@JsonProperty("Datacenter")
	@SerializedName("Datacenter")
	private String datacenter;

	@JsonProperty("Node")
	@SerializedName("Node")
	private String node;

	@JsonProperty("Address")
	@SerializedName("Address")
	private String address;

	@JsonProperty("Service")
	@SerializedName("Service")
	private Service service;

	@JsonProperty("Check")
	@SerializedName("Check")
	private Check check;

	@JsonProperty("WriteRequest")
	@SerializedName("WriteRequest")
	private WriteRequest writeRequest;

	@JsonProperty("NodeMeta")
	@SerializedName("NodeMeta")
	private Map<String, String> nodeMeta;

	@JsonProperty("SkipNodeUpdate")
	@SerializedName("SkipNodeUpdate")
	private boolean skipNodeUpdate;

	@JsonProperty("TaggedAddresses")
	@SerializedName("TaggedAddresses")
	private Map<String, String> taggedAddresses;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Check getCheck() {
		return check;
	}

	public void setCheck(Check check) {
		this.check = check;
	}

	public WriteRequest getWriteRequest() {
		return writeRequest;
	}

	public void setWriteRequest(WriteRequest writeRequest) {
		this.writeRequest = writeRequest;
	}

	public Map<String, String> getNodeMeta() {
		return nodeMeta;
	}

	public void setNodeMeta(Map<String, String> nodeMeta) {
		this.nodeMeta = nodeMeta;
	}

	public boolean isSkipNodeUpdate() {
		return skipNodeUpdate;
	}

	public void setSkipNodeUpdate(boolean skipNodeUpdate) {
		this.skipNodeUpdate = skipNodeUpdate;
	}

	public Map<String, String> getTaggedAddresses() {
		return taggedAddresses;
	}

	public void setTaggedAddresses(Map<String, String> taggedAddresses) {
		this.taggedAddresses = taggedAddresses;
	}

	@Override
	public String toString() {
		return "CatalogRegistration{" + "datacenter='" + datacenter + '\'' + ", node='" + node + '\'' + ", address='"
				+ address + '\'' + ", service=" + service + ", check=" + check + ", writeRequest=" + writeRequest
				+ ", nodeMeta=" + nodeMeta + ", skipNodeUpdate=" + skipNodeUpdate + ", taggedAddresses="
				+ taggedAddresses + '}';
	}
}