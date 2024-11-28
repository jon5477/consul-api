package com.ecwid.consul.v1;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.transport.HttpResponse;

/**
 * Represents a Consul response from the Consul API.
 * 
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class Response<T> {
	private final T value;
	private final Long consulIndex;
	private final Boolean consulKnownLeader;
	private final Long consulLastContact;

	public Response(@Nullable T value, @Nullable Long consulIndex, @Nullable Boolean consulKnownLeader,
			@Nullable Long consulLastContact) {
		this.value = value;
		this.consulIndex = consulIndex;
		this.consulKnownLeader = consulKnownLeader;
		this.consulLastContact = consulLastContact;
	}

	public Response(@Nullable T value, @NonNull HttpResponse httpResponse) {
		this(value, httpResponse.getConsulIndex(), httpResponse.isConsulKnownLeader(),
				httpResponse.getConsulLastContact());
	}

	@Nullable
	public final T getValue() {
		return value;
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
		return "Response [value=" + value + ", consulIndex=" + consulIndex + ", consulKnownLeader=" + consulKnownLeader
				+ ", consulLastContact=" + consulLastContact + "]";
	}
}