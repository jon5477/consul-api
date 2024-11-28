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
	private final String consulEffectiveConsistency;
	private final Boolean consulKnownLeader;
	private final Long consulLastContact;
	private final Long consulIndex;

	public HttpResponse(int statusCode, @NonNull String statusMessage, @Nullable JsonNode content,
			@Nullable String consulEffectiveConsistency, @Nullable Boolean consulKnownLeader,
			@Nullable Long consulLastContact, @Nullable Long consulIndex) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.content = content;
		this.consulEffectiveConsistency = consulEffectiveConsistency;
		this.consulKnownLeader = consulKnownLeader;
		this.consulLastContact = consulLastContact;
		this.consulIndex = consulIndex;
	}

	public final int getStatusCode() {
		return statusCode;
	}

	@NonNull
	public final String getStatusMessage() {
		return statusMessage;
	}

	@Nullable
	public final JsonNode getContent() {
		return content;
	}

	@Nullable
	public final String getConsulEffectiveConsistency() {
		return consulEffectiveConsistency;
	}

	@Nullable
	public final Long getConsulIndex() {
		return consulIndex;
	}

	@Nullable
	public final Boolean isConsulKnownLeader() {
		return consulKnownLeader;
	}

	@Nullable
	public final Long getConsulLastContact() {
		return consulLastContact;
	}

	@Override
	public final String toString() {
		return "HttpResponse [statusCode=" + statusCode + ", statusMessage=" + statusMessage + ", content=" + content
				+ ", consulEffectiveConsistency=" + consulEffectiveConsistency + ", consulKnownLeader="
				+ consulKnownLeader + ", consulLastContact=" + consulLastContact + ", consulIndex=" + consulIndex + "]";
	}
}