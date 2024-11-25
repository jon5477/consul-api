package com.ecwid.consul.v1.kv.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ecwid.consul.UrlParameters;
import com.ecwid.consul.Utils;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class PutParams implements UrlParameters {
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
	public List<String> toUrlParameters() {
		List<String> params = new ArrayList<>();

		if (flags != 0) {
			params.add("flags=" + flags);
		}
		if (cas != null) {
			params.add("cas=" + cas);
		}
		if (acquireSession != null) {
			params.add("acquire=" + Utils.encodeValue(acquireSession));
		}
		if (releaseSession != null) {
			params.add("release=" + Utils.encodeValue(releaseSession));
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