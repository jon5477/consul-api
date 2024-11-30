package com.ecwid.consul;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 *
 */
public final class SingleQueryParameters implements QueryParameters {
	private final String key;
	private final String value;

	public SingleQueryParameters(String key) {
		this(key, null);
	}

	public SingleQueryParameters(String key, String value) {
		this.key = Objects.requireNonNull(key, "key cannot be null");
		this.value = value;
	}

	@Override
	public Map<String, String> getQueryParameters() {
		return Collections.singletonMap(key, value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SingleQueryParameters)) {
			return false;
		}
		SingleQueryParameters other = (SingleQueryParameters) obj;
		return Objects.equals(key, other.key) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "SingleQueryParameters [key=" + key + ", value=" + value + "]";
	}
}