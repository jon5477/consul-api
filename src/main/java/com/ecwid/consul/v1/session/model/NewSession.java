package com.ecwid.consul.v1.session.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class NewSession {
	@JsonProperty("LockDelay")
	@SerializedName("LockDelay")
	private long lockDelay;

	@JsonProperty("Name")
	@SerializedName("Name")
	private String name;

	@JsonProperty("Node")
	@SerializedName("Node")
	private String node;

	@JsonProperty("Checks")
	@SerializedName("Checks")
	private List<String> checks;

	@JsonProperty("Behavior")
	@SerializedName("Behavior")
	private Session.Behavior behavior;

	@JsonProperty("TTL")
	@SerializedName("TTL")
	private String ttl;

	public long getLockDelay() {
		return lockDelay;
	}

	public void setLockDelay(long lockDelayInSeconds) {
		this.lockDelay = lockDelayInSeconds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public List<String> getChecks() {
		return checks;
	}

	public void setChecks(List<String> checks) {
		this.checks = checks;
	}

	public Session.Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Session.Behavior behavior) {
		this.behavior = behavior;
	}

	public String getTtl() {
		return ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}
}