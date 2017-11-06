package com.learn.mongo.demo.productCategory;

public class Dimensions {
	private Integer width;
	private Integer height;
	private Integer depth;

	public Dimensions() {
		super();
	}

	public Dimensions(Integer width, Integer height, Integer depth) {
		super();
		this.width = width;
		this.height = height;
		this.depth = depth;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "Dimensions [width=" + width + ", height=" + height + ", depth="
				+ depth + "]";
	}

}
