package com.ecwid.consul.v1.agent.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class Member {
	@JsonProperty("Name")
	private String name;

	@JsonProperty("Addr")
	private String address;

	@JsonProperty("Port")
	private Integer port;

	@JsonProperty("Tags")
	private Map<String, String> tags;

	@JsonProperty("Status")
	private int status;

	@JsonProperty("ProtocolMin")
	private int protocolMin;

	@JsonProperty("ProtocolMax")
	private int protocolMax;

	@JsonProperty("ProtocolCur")
	private int protocolCur;

	@JsonProperty("DelegateMin")
	private int delegateMin;

	@JsonProperty("DelegateMax")
	private int delegateMax;

	@JsonProperty("DelegateCur")
	private int delegateCur;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getProtocolMin() {
		return protocolMin;
	}

	public void setProtocolMin(int protocolMin) {
		this.protocolMin = protocolMin;
	}

	public int getProtocolMax() {
		return protocolMax;
	}

	public void setProtocolMax(int protocolMax) {
		this.protocolMax = protocolMax;
	}

	public int getProtocolCur() {
		return protocolCur;
	}

	public void setProtocolCur(int protocolCur) {
		this.protocolCur = protocolCur;
	}

	public int getDelegateMin() {
		return delegateMin;
	}

	public void setDelegateMin(int delegateMin) {
		this.delegateMin = delegateMin;
	}

	public int getDelegateMax() {
		return delegateMax;
	}

	public void setDelegateMax(int delegateMax) {
		this.delegateMax = delegateMax;
	}

	public int getDelegateCur() {
		return delegateCur;
	}

	public void setDelegateCur(int delegateCur) {
		this.delegateCur = delegateCur;
	}

	@Override
	public String toString() {
		return "Member{" + "name='" + name + '\'' + ", address='" + address + '\'' + ", port=" + port + ", tags=" + tags
				+ ", status=" + status + ", protocolMin=" + protocolMin + ", protocolMax=" + protocolMax
				+ ", protocolCur=" + protocolCur + ", delegateMin=" + delegateMin + ", delegateMax=" + delegateMax
				+ ", delegateCur=" + delegateCur + '}';
	}
}
