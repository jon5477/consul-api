package com.ecwid.consul.v1.acl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ecwid.consul.ConsulRequest;
import com.ecwid.consul.SingleUrlParameters;
import com.ecwid.consul.UrlParameters;

/**
 * @author Jon Huang (jon5477)
 */
public class AclTokensRequest implements ConsulRequest {
	private final String policy;
	private final String role;
	private final String serviceName;
	private final String authMethod;
	private final String authMethodNs;
	private final String ns;

	private AclTokensRequest(Builder b) {
		this.policy = b.policy;
		this.role = b.role;
		this.serviceName = b.serviceName;
		this.authMethod = b.authMethod;
		this.authMethodNs = b.authMethodNs;
		this.ns = b.ns;
	}

	public static final class Builder {
		private String policy;
		private String role;
		private String serviceName;
		private String authMethod;
		private String authMethodNs;
		private String ns;

		private Builder() {
		}

		public final Builder setPolicy(String policy) {
			this.policy = policy;
			return this;
		}

		public final Builder setRole(String role) {
			this.role = role;
			return this;
		}

		public final Builder setServiceName(String serviceName) {
			this.serviceName = serviceName;
			return this;
		}

		public final Builder setAuthMethod(String authMethod) {
			this.authMethod = authMethod;
			return this;
		}

		public final Builder setAuthMethodNs(String authMethodNs) {
			this.authMethodNs = authMethodNs;
			return this;
		}

		public final Builder setNs(String ns) {
			this.ns = ns;
			return this;
		}

		public final AclTokensRequest build() {
			return new AclTokensRequest(this);
		}
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	@Override
	public final List<UrlParameters> asUrlParameters() {
		List<UrlParameters> params = new ArrayList<>();
		if (policy != null) {
			params.add(new SingleUrlParameters("policy", policy));
		}
		if (role != null) {
			params.add(new SingleUrlParameters("role", role));
		}
		if (serviceName != null) {
			params.add(new SingleUrlParameters("servicename", serviceName));
		}
		if (authMethod != null) {
			params.add(new SingleUrlParameters("authmethod", authMethod));
		}
		if (authMethodNs != null) {
			params.add(new SingleUrlParameters("authmethod-ns", authMethodNs));
		}
		if (ns != null) {
			params.add(new SingleUrlParameters("ns", ns));
		}
		return params;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(authMethod, authMethodNs, ns, policy, role, serviceName);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AclTokensRequest)) {
			return false;
		}
		AclTokensRequest other = (AclTokensRequest) obj;
		return Objects.equals(authMethod, other.authMethod) && Objects.equals(authMethodNs, other.authMethodNs)
				&& Objects.equals(ns, other.ns) && Objects.equals(policy, other.policy)
				&& Objects.equals(role, other.role) && Objects.equals(serviceName, other.serviceName);
	}

	@Override
	public final String toString() {
		return "AclTokensRequest [policy=" + policy + ", role=" + role + ", serviceName=" + serviceName
				+ ", authMethod=" + authMethod + ", authMethodNs=" + authMethodNs + ", ns=" + ns + "]";
	}
}