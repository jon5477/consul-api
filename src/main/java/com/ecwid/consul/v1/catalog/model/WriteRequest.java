package com.ecwid.consul.v1.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class WriteRequest {
	@JsonProperty("Token")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "WriteRequest{" + "token='" + token + '\'' + '}';
	}
}