package com.ecwid.consul.v1.health.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class HealthService {
	@JsonProperty("Node")
	private Node node;

	@JsonProperty("Service")
	private Service service;

	@JsonProperty("Checks")
	private List<Check> checks;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public List<Check> getChecks() {
		return checks;
	}

	public void setChecks(List<Check> checks) {
		this.checks = checks;
	}

	@Override
	public int hashCode() {
		return Objects.hash(checks, node, service);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof HealthService)) {
			return false;
		}
		HealthService other = (HealthService) obj;
		return Objects.equals(checks, other.checks) && Objects.equals(node, other.node)
				&& Objects.equals(service, other.service);
	}

	@Override
	public String toString() {
		return "HealthService [node=" + node + ", service=" + service + ", checks=" + checks + "]";
	}
}