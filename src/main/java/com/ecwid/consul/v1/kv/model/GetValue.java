package com.ecwid.consul.v1.kv.model;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class GetValue {
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
	private String value;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDecodedValue(Charset charset) {
		if (value == null) {
			return null;
		}
		if (charset == null) {
			charset = StandardCharsets.UTF_8;
		}
		return new String(Base64.getDecoder().decode(value), charset);
	}

	public String getDecodedValue() {
		return getDecodedValue(StandardCharsets.UTF_8);
	}

	@Override
	public String toString() {
		return "GetValue{" +
				"createIndex=" + createIndex +
				", modifyIndex=" + modifyIndex +
				", lockIndex=" + lockIndex +
				", flags=" + flags +
				", session='" + session + '\'' +
				", key='" + key + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
