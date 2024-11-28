package com.ecwid.consul.transport;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents a HTTP response from the Consul API.
 * 
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class HttpResponse {
	private final int statusCode;
	private final String statusMessage;
	private final JsonNode content;
	private final Long consulIndex;
	private final Boolean consulKnownLeader;
	private final Long consulLastContact;

	public HttpResponse(int statusCode, @NonNull String statusMessage, @Nullable JsonNode content,
			@Nullable Long consulIndex, @Nullable Boolean consulKnownLeader, @Nullable Long consulLastContact) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.content = content;
		this.consulIndex = consulIndex;
		this.consulKnownLeader = consulKnownLeader;
		this.consulLastContact = consulLastContact;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public boolean isSuccess() {
		return statusCode == 200;
	}

	@NonNull
	public String getStatusMessage() {
		return statusMessage;
	}

	@Nullable
	public JsonNode getContent() {
		return content;
	}

	@Nullable
	public Long getConsulIndex() {
		return consulIndex;
	}

	@Nullable
	public Boolean isConsulKnownLeader() {
		return consulKnownLeader;
	}

	@Nullable
	public Long getConsulLastContact() {
		return consulLastContact;
	}
}