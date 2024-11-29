package com.ecwid.consul.v1;

import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.transport.ConsulHttpResponse;

/**
 * Represents a Consul response from the Consul API.
 * 
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class Response<T> {
	private final T value;
	private final String consulEffectiveConsistency;
	private final Boolean consulKnownLeader;
	private final Long consulLastContact;
	private final Long consulIndex;

	public Response(@Nullable T value, @Nullable String consulEffectiveConsistency, @Nullable Boolean consulKnownLeader,
			@Nullable Long consulLastContact, @Nullable Long consulIndex) {
		this.value = value;
		this.consulEffectiveConsistency = consulEffectiveConsistency;
		this.consulKnownLeader = consulKnownLeader;
		this.consulLastContact = consulLastContact;
		this.consulIndex = consulIndex;
	}

	public Response(@Nullable T value, @NonNull ConsulHttpResponse resp) {
		Objects.requireNonNull(resp, "http response cannot be null");
		this.value = value;
		this.consulEffectiveConsistency = resp.getConsulEffectiveConsistency();
		this.consulKnownLeader = resp.isConsulKnownLeader();
		this.consulLastContact = resp.getConsulLastContact();
		this.consulIndex = resp.getConsulIndex();
	}

	@Nullable
	public final T getValue() {
		return value;
	}

	@Nullable
	public final String getConsulEffectiveConsistency() {
		return consulEffectiveConsistency;
	}

	@Nullable
	public final Boolean isConsulKnownLeader() {
		return consulKnownLeader;
	}

	@Nullable
	public final Long getConsulLastContact() {
		return consulLastContact;
	}

	@Nullable
	public final Long getConsulIndex() {
		return consulIndex;
	}

	@Override
	public final String toString() {
		return "Response [value=" + value + ", consulIndex=" + consulIndex + ", consulKnownLeader=" + consulKnownLeader
				+ ", consulLastContact=" + consulLastContact + "]";
	}
}