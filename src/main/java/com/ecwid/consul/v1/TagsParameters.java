package com.ecwid.consul.v1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ecwid.consul.QueryParameters;

@Deprecated(forRemoval = true)
public final class TagsParameters implements QueryParameters {
	private final String[] tags;

	public TagsParameters(String[] tags) {
		this.tags = tags;
	}

	@Override
	public Map<String, String> getQueryParameters() {
		Map<String, String> params = new HashMap<>();
		if (tags != null) {
			for (String tag : tags) {
				if (tag != null) {
					params.put("tag", tag);
				}
			}
		}
		return params;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TagsParameters)) {
			return false;
		}
		TagsParameters that = (TagsParameters) o;
		return Arrays.equals(tags, that.tags);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(tags);
	}
}