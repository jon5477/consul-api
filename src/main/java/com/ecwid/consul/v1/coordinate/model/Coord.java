package com.ecwid.consul.v1.coordinate.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vasily Vasilkov (vgv@ecwid.com)
 */
public class Coord {
	@JsonProperty("Error")
	private Double error;
	@JsonProperty("Height")
	private Double height;
	@JsonProperty("Adjustment")
	private Double adjustment;
	@JsonProperty("Vec")
	private List<Double> vec;

	public Double getError() {
		return error;
	}

	public void setError(Double error) {
		this.error = error;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public List<Double> getVec() {
		return vec;
	}

	public void setVec(List<Double> vec) {
		this.vec = vec;
	}

	@Override
	public String toString() {
		return "Coord{" + "error=" + error + ", height=" + height + ", adjustment=" + adjustment + ", vec=" + vec + '}';
	}
}