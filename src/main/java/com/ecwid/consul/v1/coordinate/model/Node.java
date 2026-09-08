package com.ecwid.consul.v1.coordinate.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 * @author Jon Huang (jon5477)
 */
public class Node {
	@JsonProperty("Node")
	private String node;

	@JsonProperty("Segment")
	private String segment;

	@JsonProperty("Coord")
	private Coord coord;

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coord, node, segment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		Node other = (Node) obj;
		return Objects.equals(coord, other.coord) && Objects.equals(node, other.node)
				&& Objects.equals(segment, other.segment);
	}

	@Override
	public String toString() {
		return "Node [node=" + node + ", segment=" + segment + ", coord=" + coord + "]";
	}
}