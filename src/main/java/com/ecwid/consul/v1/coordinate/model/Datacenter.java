package com.ecwid.consul.v1.coordinate.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class Datacenter {
	@JsonProperty("Datacenter")
	private String datacenter;

	@JsonProperty("AreaID")
	private String areaId;

	@JsonProperty("Coordinates")
	private List<Node> coordinates;

	public String getDatacenter() {
		return datacenter;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public List<Node> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Node> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return "Datacenter{" + "datacenter='" + datacenter + '\'' + ", areaId='" + areaId + '\'' + ", coordinates="
				+ coordinates + '}';
	}
}