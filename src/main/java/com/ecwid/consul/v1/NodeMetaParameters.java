package com.ecwid.consul.v1;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;

@Deprecated(forRemoval = true)
public final class NodeMetaParameters implements QueryParameters {
	private final Map<String, String> nodeMeta;

	public NodeMetaParameters(Map<String, String> nodeMeta) {
		this.nodeMeta = nodeMeta;
	}

	@Override
	public Map<String, String> getQueryParameters() {
//		List<String> params = new ArrayList<>();
//		if (nodeMeta != null) {
//			String key = "node-meta";
//			for (Map.Entry<String, String> entry : nodeMeta.entrySet()) {
//				String value = entry.getKey() + ":" + entry.getValue();
//				params.add(key + "=" + value);
//			}
//		}
//		return params;
		return Collections.emptyMap();
	}

	@Override
	public int hashCode() {
		return Objects.hash(nodeMeta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof NodeMetaParameters)) {
			return false;
		}
		NodeMetaParameters other = (NodeMetaParameters) obj;
		return Objects.equals(nodeMeta, other.nodeMeta);
	}
}