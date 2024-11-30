package com.ecwid.consul.v1.event.model;

import java.util.Objects;
import java.util.UUID;

import com.ecwid.consul.Utils;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class Event {
	@JsonProperty("ID")
	private UUID id;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("Payload")
	private String payload;

	@JsonProperty("NodeFilter")
	private String nodeFilter;

	@JsonProperty("ServiceFilter")
	private String serviceFilter;

	@JsonProperty("TagFilter")
	private String tagFilter;

	@JsonProperty("Version")
	private int version;

	@JsonProperty("LTime")
	private int lTime;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getNodeFilter() {
		return nodeFilter;
	}

	public void setNodeFilter(String nodeFilter) {
		this.nodeFilter = nodeFilter;
	}

	public String getServiceFilter() {
		return serviceFilter;
	}

	public void setServiceFilter(String serviceFilter) {
		this.serviceFilter = serviceFilter;
	}

	public String getTagFilter() {
		return tagFilter;
	}

	public void setTagFilter(String tagFilter) {
		this.tagFilter = tagFilter;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getlTime() {
		return lTime;
	}

	public void setlTime(int lTime) {
		this.lTime = lTime;
	}

	/**
	 * Generates a wait index that matches the behavior of Consul's index
	 * generation.
	 * 
	 * @deprecated Use {@link Utils#getWaitIndex(UUID)} with this Event ID.
	 * @return The Consul wait index to use.
	 */
	@Deprecated(since = "2.0.0", forRemoval = true)
	public long getWaitIndex() {
		return Utils.getWaitIndex(id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, lTime, name, nodeFilter, payload, serviceFilter, tagFilter, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Event)) {
			return false;
		}
		Event other = (Event) obj;
		return Objects.equals(id, other.id) && lTime == other.lTime && Objects.equals(name, other.name)
				&& Objects.equals(nodeFilter, other.nodeFilter) && Objects.equals(payload, other.payload)
				&& Objects.equals(serviceFilter, other.serviceFilter) && Objects.equals(tagFilter, other.tagFilter)
				&& version == other.version;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", payload=" + payload + ", nodeFilter=" + nodeFilter
				+ ", serviceFilter=" + serviceFilter + ", tagFilter=" + tagFilter + ", version=" + version + ", lTime="
				+ lTime + "]";
	}
}