package com.ecwid.consul;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class SingleUrlParameters implements UrlParameters {
	private final String key;
	private final String value;

	public SingleUrlParameters(String key) {
		this.key = key;
		this.value = null;
	}

	public SingleUrlParameters(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public List<String> toUrlParameters() {
		if (value != null) {
			return Collections.singletonList(key + "=" + Utils.encodeValue(value));
		} else {
			return Collections.singletonList(key);
		}
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
		if (!(obj instanceof SingleUrlParameters)) {
			return false;
		}
		SingleUrlParameters other = (SingleUrlParameters) obj;
		return Objects.equals(key, other.key) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "SingleUrlParameters [key=" + key + ", value=" + value + "]";
	}
}