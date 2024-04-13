package com.ecwid.consul.v1.acl.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Jon Huang (jon5477)
 */
@JsonInclude(Include.NON_NULL)
public final class AclPolicy {
	@JsonProperty("ID")
	private String id;
	@JsonProperty("Name")
	private String name;

	public AclPolicy() {
	}

	public AclPolicy(String id, String name) {
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
		if (!(obj instanceof AclPolicy)) {
			return false;
		}
		AclPolicy other = (AclPolicy) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public final String toString() {
		return "AclPolicy [id=" + id + ", name=" + name + "]";
	}
}