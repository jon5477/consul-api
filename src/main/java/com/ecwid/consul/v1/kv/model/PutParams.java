package com.ecwid.consul.v1.kv.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ecwid.consul.QueryParameters;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class PutParams implements QueryParameters {
	private long flags;
	private Long cas;
	private String acquireSession;
	private String releaseSession;

	public long getFlags() {
		return flags;
	}

	public void setFlags(long flags) {
		this.flags = flags;
	}

	public Long getCas() {
		return cas;
	}

	public void setCas(Long cas) {
		this.cas = cas;
	}

	public String getAcquireSession() {
		return acquireSession;
	}

	public void setAcquireSession(String acquireSession) {
		this.acquireSession = acquireSession;
	}

	public String getReleaseSession() {
		return releaseSession;
	}

	public void setReleaseSession(String releaseSession) {
		this.releaseSession = releaseSession;
	}

	@Override
	public Map<String, String> getQueryParameters() {
		Map<String, String> params = new HashMap<>();
		if (flags != 0) {
			params.put("flags", String.valueOf(flags));
		}
		if (cas != null) {
			params.put("cas", String.valueOf(cas));
		}
		if (acquireSession != null) {
			params.put("acquire", acquireSession);
		}
		if (releaseSession != null) {
			params.put("release", releaseSession);
		}
		return params;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PutParams)) {
			return false;
		}
		PutParams putParams = (PutParams) o;
		return flags == putParams.flags && Objects.equals(cas, putParams.cas)
				&& Objects.equals(acquireSession, putParams.acquireSession)
				&& Objects.equals(releaseSession, putParams.releaseSession);
	}

	@Override
	public int hashCode() {
		return Objects.hash(flags, cas, acquireSession, releaseSession);
	}
}