package com.ecwid.consul;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.event.model.Event;

/**
 * Provides various utility functions used by the Consul API client library.
 * 
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class Utils {
	private Utils() {
		// static utility class
	}

	/**
	 * Converts the given {@link CharSequence} into a {@code char[]}.
	 * 
	 * @param seq The {@link CharSequence} to convert, cannot be {@code null}.
	 * @return The given {@link CharSequence} as an {@code char[]}.
	 */
	public static char[] charSequenceToArray(@NonNull CharSequence seq) {
		int length = seq.length();
		char[] arr = new char[length];
		for (int i = 0; i < length; i++) {
			arr[i] = seq.charAt(i);
		}
		return arr;
	}

	/**
	 * Determines if the given {@link CharSequence}s are equal.
	 * 
	 * @param cs1 The first {@link CharSequence}
	 * @param cs2 The second {@link CharSequence}
	 * @return {@code true} if both {@link CharSequence}s are equal, {@code false}
	 *         otherwise
	 */
	public static boolean charSequenceEquals(@Nullable CharSequence cs1, @Nullable CharSequence cs2) {
		if (cs1 == cs2) {
			return true;
		}
		if (cs1 == null || cs2 == null || cs1.length() != cs2.length()) {
			return false;
		}
		for (int i = 0, n = cs1.length(); i < n; i++) {
			if (cs1.charAt(i) != cs2.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Converts the given {@link Duration} instance into a Consul duration
	 * specifier.
	 * 
	 * @param dur The {@link Duration} instance to convert.
	 * @return A {@link String} duration specifier for Consul.
	 */
	public static String toConsulDuration(@NonNull Duration dur) {
		Objects.requireNonNull(dur, "duration cannot be null");
		if (dur.isNegative()) {
			throw new IllegalArgumentException("duration cannot be negative");
		}
		if (dur.isZero()) {
			return "0s";
		}
		long hours = dur.toHours();
		long minutes = dur.toMinutesPart();
		long seconds = dur.toSecondsPart();
		long millis = dur.toMillisPart();
		long nanosPart = dur.toNanosPart() % 1000000;
		long micros = nanosPart / 1000;
		long nanos = nanosPart % 1000;
		StringBuilder sb = new StringBuilder();
		// Append hours
		if (hours > 0) {
			sb.append(hours).append("h");
		}
		// Append minutes
		if (minutes > 0) {
			sb.append(minutes).append("m");
		}
		// Append seconds
		if (seconds > 0) {
			sb.append(seconds).append("s");
		}
		// Append milliseconds
		if (millis > 0) {
			sb.append(millis).append("ms");
		}
		// Append microseconds
		if (micros > 0) {
			sb.append(micros).append("µs");
		}
		// Append nanoseconds
		if (nanos > 0) {
			sb.append(nanos).append("ns");
		}
		return sb.toString();
	}

	/**
	 * Converts the given duration in seconds to a seconds specifier for Consul.
	 * 
	 * @deprecated Use {@link #toConsulDuration(Duration)} instead.
	 * @param waitTime The duration in seconds.
	 * @return The time specifier as a string.
	 */
	@Deprecated(since = "2.0.0", forRemoval = true)
	public static String toSecondsString(long waitTime) {
		return String.valueOf(waitTime) + "s";
	}

	public static String assembleAgentAddress(String host, int port, String path) {
		String agentPath = "";
		if (path != null && !path.trim().isEmpty()) {
			agentPath = "/" + path;
		}
		return host + ":" + port + agentPath;
	}

	/**
	 * This is code converted from <a href=
	 * "https://github.com/hashicorp/consul/blob/beef7a7417a22f1dc7c436de531704fd206b0b4c/api/event.go#L102-L114">Consul's
	 * code</a>
	 * 
	 * This is a hack. It simulates the index generation to convert an
	 * {@link Event#getId()} into a wait <a href=
	 * "https://developer.hashicorp.com/consul/api-docs/features/blocking">index</a>.
	 *
	 * @param id The {@link UUID} to convert.
	 * @return A wait index value suitable for passing to
	 *         {@link QueryParams.Builder#setIndex(long)} for blocking calls.
	 */
	public static long getWaitIndex(@NonNull UUID id) {
		Objects.requireNonNull(id, "UUID cannot be null");
		String uuid = id.toString();
		String lower = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18);
		String upper = uuid.substring(19, 23) + uuid.substring(24);
		long lowVal = Long.parseUnsignedLong(lower, 16);
		long highVal = Long.parseUnsignedLong(upper, 16);
		return lowVal ^ highVal;
	}
}