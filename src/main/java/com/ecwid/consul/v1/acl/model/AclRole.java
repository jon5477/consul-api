package com.ecwid.consul.v1.acl.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author Jon Huang (jon5477)
 */
public final class AclRole {
	@JsonProperty("ID")
	@SerializedName("ID")
	private String id;
	@JsonProperty("Name")
	@SerializedName("Name")
	private String name;

	public AclRole() {
	}

	public AclRole(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AclRole)) {
			return false;
		}
		AclRole other = (AclRole) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public final String toString() {
		return "AclRole [id=" + id + ", name=" + name + "]";
	}
}