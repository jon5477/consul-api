package com.ecwid.consul.v1.acl.model;

import java.time.ZonedDateTime;
import java.util.List;

public final class AclToken {
	private String accessorId;
	private String description;
	private List<AclPolicy> policies;
	private boolean local;
	private ZonedDateTime createTime;
	private String hash;
	private int createIndex;
	private int modifyIndex;

	public final String getAccessorId() {
		return accessorId;
	}

	public final void setAccessorId(String accessorId) {
		this.accessorId = accessorId;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final List<AclPolicy> getPolicies() {
		return policies;
	}

	public final void setPolicies(List<AclPolicy> policies) {
		this.policies = policies;
	}

	public final boolean isLocal() {
		return local;
	}

	public final void setLocal(boolean local) {
		this.local = local;
	}

	public final ZonedDateTime getCreateTime() {
		return createTime;
	}

	public final void setCreateTime(ZonedDateTime createTime) {
		this.createTime = createTime;
	}

	public final String getHash() {
		return hash;
	}

	public final void setHash(String hash) {
		this.hash = hash;
	}

	public final int getCreateIndex() {
		return createIndex;
	}

	public final void setCreateIndex(int createIndex) {
		this.createIndex = createIndex;
	}

	public final int getModifyIndex() {
		return modifyIndex;
	}

	public final void setModifyIndex(int modifyIndex) {
		this.modifyIndex = modifyIndex;
	}

	@Override
	public final String toString() {
		return "AclToken [accessorId=" + accessorId + ", description=" + description + ", policies=" + policies
				+ ", local=" + local + ", createTime=" + createTime + ", hash=" + hash + ", createIndex=" + createIndex
				+ ", modifyIndex=" + modifyIndex + "]";
	}
}