package com.ecwid.consul.v1;

import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.QueryParameters;
import com.ecwid.consul.Utils;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public final class QueryParams implements QueryParameters {
	public static final Duration MAX_WAIT_TIME = Duration.ofMinutes(10);

	public static final class Builder {
		private String datacenter;
		private ConsistencyMode consistencyMode;
		private Duration waitTime;
		private long index;
		private String near;

		public Builder() {
			this.datacenter = null;
			this.consistencyMode = ConsistencyMode.DEFAULT;
			this.waitTime = null;
			this.index = -1;
			this.near = null;
		}

		public Builder setConsistencyMode(ConsistencyMode consistencyMode) {
			this.consistencyMode = consistencyMode;
			return this;
		}

		public Builder setDatacenter(String datacenter) {
			this.datacenter = datacenter;
			return this;
		}

		/**
		 * Sets the maximum wait time {@link Duration} for the blocking request.
		 * 
		 * From <a href=
		 * "https://developer.hashicorp.com/consul/api-docs/features/blocking">Consul
		 * documentation</a>: This is limited to 10 minutes. If not set, the wait time
		 * defaults to 5 minutes.
		 * 
		 * @param waitTime The maximum wait time {@link Duration} for the blocking
		 *                 request.
		 * @return The {@link Builder} instance for chaining.
		 */
		public Builder setWaitTime(@Nullable Duration waitTime) {
			if (waitTime != null) {
				if (waitTime.isNegative() || waitTime.isZero()) {
					throw new IllegalArgumentException("wait time duration must be greater than zero");
				}
				if (waitTime.compareTo(MAX_WAIT_TIME) > 0) {
					throw new IllegalArgumentException("wait time duration cannot exceed 10 minutes");
				}
			}
			this.waitTime = waitTime;
			return this;
		}

		public Builder setIndex(long index) {
			this.index = index;
			return this;
		}

		public Builder setNear(String near) {
			this.near = near;
			return this;
		}

		public QueryParams build() {
			return new QueryParams(datacenter, consistencyMode, waitTime, index, near);
		}
	}

	public static final QueryParams DEFAULT = new QueryParams(ConsistencyMode.DEFAULT);

	private final String datacenter;
	private final ConsistencyMode consistencyMode;
	private final Duration waitTime;
	private final long index;
	private final String near;

	private QueryParams(String datacenter, ConsistencyMode consistencyMode, @Nullable Duration waitTime, long index,
			@Nullable String near) {
		this.datacenter = datacenter;
		this.consistencyMode = consistencyMode;
		this.waitTime = waitTime;
		this.index = index;
		this.near = near;
	}

	private QueryParams(String datacenter, ConsistencyMode consistencyMode, @Nullable Duration waitTime, long index) {
		this(datacenter, consistencyMode, waitTime, index, null);
	}

	public QueryParams(String datacenter) {
		this(datacenter, ConsistencyMode.DEFAULT, null, -1);
	}

	public QueryParams(ConsistencyMode consistencyMode) {
		this(null, consistencyMode, null, -1);
	}

	public QueryParams(String datacenter, ConsistencyMode consistencyMode) {
		this(datacenter, consistencyMode, null, -1);
	}

	public QueryParams(Duration waitTime, long index) {
		this(null, ConsistencyMode.DEFAULT, waitTime, index);
	}

	public QueryParams(String datacenter, Duration waitTime, long index) {
		this(datacenter, ConsistencyMode.DEFAULT, waitTime, index, null);
	}

	public String getDatacenter() {
		return datacenter;
	}

	public ConsistencyMode getConsistencyMode() {
		return consistencyMode;
	}

	public Duration getWaitTime() {
		return waitTime;
	}

	public long getIndex() {
		return index;
	}

	public String getNear() {
		return near;
	}

	@Override
	public Map<String, String> getQueryParameters() {
		Map<String, String> params = new HashMap<>();
		// add basic params
		if (datacenter != null) {
			params.put("dc", datacenter);
		}
		if (consistencyMode != ConsistencyMode.DEFAULT) {
			params.put(consistencyMode.name().toLowerCase(Locale.ROOT), null);
		}
		if (waitTime != null) {
			params.put("wait", Utils.toConsulDuration(waitTime));
		}
		if (index != -1) {
			params.put("index", Long.toUnsignedString(index));
		}
		if (near != null) {
			params.put("near", near);
		}
		return params;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof QueryParams)) {
			return false;
		}
		QueryParams that = (QueryParams) o;
		return Objects.equals(waitTime, that.waitTime) && index == that.index
				&& Objects.equals(datacenter, that.datacenter) && consistencyMode == that.consistencyMode
				&& Objects.equals(near, that.near);
	}

	@Override
	public int hashCode() {
		return Objects.hash(datacenter, consistencyMode, waitTime, index, near);
	}
}
