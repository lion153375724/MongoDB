package com.learn.mongo.demo.productCategory;

public class Pricing {
	private Double list;
	private Double retail;
	private Integer savings;
	private Integer pct_savings;

	public Pricing() {
		super();
	}

	public Pricing(Double list, Double retail, Integer savings,
			Integer pct_savings) {
		super();
		this.list = list;
		this.retail = retail;
		this.savings = savings;
		this.pct_savings = pct_savings;
	}

	public Double getList() {
		return list;
	}

	public void setList(Double list) {
		this.list = list;
	}

	public Double getRetail() {
		return retail;
	}

	public void setRetail(Double retail) {
		this.retail = retail;
	}

	public Integer getSavings() {
		return savings;
	}

	public void setSavings(Integer savings) {
		this.savings = savings;
	}

	public Integer getPct_savings() {
		return pct_savings;
	}

	public void setPct_savings(Integer pct_savings) {
		this.pct_savings = pct_savings;
	}

}
