package com.ecwid.consul.v1.catalog.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class CatalogRegistration {
	public static class Service {
		@JsonProperty("ID")
		private String id;

		@JsonProperty("Service")
		private String service;

		@JsonProperty("Tags")
		private List<String> tags;

		@JsonProperty("Address")
		private String address;

		@JsonProperty("Meta")
		private Map<String, String> meta;

		@JsonProperty("Port")
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
		UNKNOWN, @JsonProperty("passing")
		PASSING, @JsonProperty("warning")
		WARNING, @JsonProperty("critical")
		CRITICAL
	}

	public static class Check {
		@JsonProperty("Node")
		private String node;

		@JsonProperty("CheckID")
		private String checkId;

		@JsonProperty("Name")
		private String name;

		@JsonProperty("Notes")
		private String notes;

		@JsonProperty("Status")
		private CheckStatus status;

		@JsonProperty("ServiceID")
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

	@JsonProperty("ID")
	private UUID id;
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
	@JsonProperty("Service")
	private Service service;
	@JsonProperty("Check")
	private Check check;
	@JsonProperty("SkipNodeUpdate")
	private boolean skipNodeUpdate;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public boolean isSkipNodeUpdate() {
		return skipNodeUpdate;
	}

	public void setSkipNodeUpdate(boolean skipNodeUpdate) {
		this.skipNodeUpdate = skipNodeUpdate;
	}

	@Override
	public String toString() {
		return "CatalogRegistration [id=" + id + ", node=" + node + ", address=" + address + ", datacenter="
				+ datacenter + ", taggedAddresses=" + taggedAddresses + ", nodeMeta=" + nodeMeta + ", service="
				+ service + ", check=" + check + ", skipNodeUpdate=" + skipNodeUpdate + "]";
	}
}