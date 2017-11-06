package com.learn.mongo.demo.productCategory;

public class Shipping {
	private Integer weight;
	private Dimensions dimensions;

	public Shipping(Integer weight, Dimensions dimensions) {
		super();
		this.weight = weight;
		this.dimensions = dimensions;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Dimensions getDimensions() {
		return dimensions;
	}

	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

}
