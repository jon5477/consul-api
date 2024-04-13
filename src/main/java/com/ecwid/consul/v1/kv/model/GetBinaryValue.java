package com.ecwid.consul.v1.kv.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class GetBinaryValue {
	@JsonProperty("CreateIndex")
	private long createIndex;

	@JsonProperty("ModifyIndex")
	private long modifyIndex;

	@JsonProperty("LockIndex")
	private Long lockIndex;

	@JsonProperty("Flags")
	private long flags;

	@JsonProperty("Session")
	private String session;

	@JsonProperty("Key")
	private String key;

	@JsonProperty("Value")
	private byte[] value;

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

	public Long getLockIndex() {
		return lockIndex;
	}

	public void setLockIndex(Long lockIndex) {
		this.lockIndex = lockIndex;
	}

	public long getFlags() {
		return flags;
	}

	public void setFlags(long flags) {
		this.flags = flags;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "GetBinaryValue{" +
				"createIndex=" + createIndex +
				", modifyIndex=" + modifyIndex +
				", lockIndex=" + lockIndex +
				", flags=" + flags +
				", session='" + session + '\'' +
				", key='" + key + '\'' +
				", value=" + Arrays.toString(value) +
				'}';
	}
}