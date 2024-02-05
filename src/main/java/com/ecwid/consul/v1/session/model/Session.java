package com.ecwid.consul.v1.session.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class Session {
	public static enum Behavior {
		@JsonProperty("release")
		@SerializedName("release")
		RELEASE,

		@JsonProperty("delete")
		@SerializedName("delete")
		DELETE
	}

	@JsonProperty("LockDelay")
	@SerializedName("LockDelay")
	private long lockDelay;

	@JsonProperty("Checks")
	@SerializedName("Checks")
	private List<String> checks;

	@JsonProperty("Node")
	@SerializedName("Node")
	private String node;

	@JsonProperty("ID")
	@SerializedName("ID")
	private String id;

	@JsonProperty("Name")
	@SerializedName("Name")
	private String name;

	@JsonProperty("CreateIndex")
	@SerializedName("CreateIndex")
	private long createIndex;

	@JsonProperty("ModifyIndex")
	@SerializedName("ModifyIndex")
	private long modifyIndex;

	@JsonProperty("TTL")
	@SerializedName("TTL")
	private String ttl;

	@JsonProperty("Behavior")
	@SerializedName("Behavior")
	private Behavior behavior;

	public long getLockDelay() {
		return lockDelay;
	}

	public void setLockDelay(long lockDelay) {
		this.lockDelay = lockDelay;
	}

	public List<String> getChecks() {
		return checks;
	}

	public void setChecks(List<String> checks) {
		this.checks = checks;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreateIndex() {
		return createIndex;
	}

	public void setCreateIndex(long createIndex) {
		this.createIndex = createIndex;
	}

	public long getModifyIndex() {
		return modifyIndex;
	}

	public void setModifyIndex(long modifyIndex) {
		this.modifyIndex = modifyIndex;
	}

	public String getTtl() {
		return ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	@Override
	public String toString() {
		return "Session{" + "lockDelay=" + lockDelay + ", checks=" + checks + ", node='" + node + '\'' + ", id='" + id
				+ '\'' + ", name='" + name + '\'' + ", createIndex=" + createIndex + ", modifyIndex=" + modifyIndex
				+ ", ttl='" + ttl + '\'' + ", behavior=" + behavior + '}';
	}
}