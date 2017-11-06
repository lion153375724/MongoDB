package com.learn.mongo.demo.geo;

import java.util.Arrays;

/**
 * 位置类
 * 
 * @author jason
 * @createTime 2017年11月1日下午5:04:23
 */
public class AddressPosition {
	private String id;
	private String address; // 地名
	private AddressPoint addressPoint;
	private String title;
	private Double dis; //距离
	
	public AddressPosition(String address, AddressPoint addressPoint,
			String title) {
		super();
		this.address = address;
		this.addressPoint = addressPoint;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AddressPoint getAddressPoint() {
		return addressPoint;
	}

	public void setAddressPoint(AddressPoint addressPoint) {
		this.addressPoint = addressPoint;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getDis() {
		return dis;
	}

	public void setDis(Double dis) {
		this.dis = dis;
	}

}
