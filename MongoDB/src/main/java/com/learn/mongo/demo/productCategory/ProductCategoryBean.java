package com.learn.mongo.demo.productCategory;

/**
 * 
 * @author jason
 * @createTime 2017年10月30日上午11:20:27
 * 
 *             比如音乐专辑产品分类属性文档结构如下： { sku: "00e8da9b", type: "Audio Album",
 *             title: "A Love Supreme", description: "by John Coltrane", asin:
 *             "B0000A118M",
 * 
 *             shipping: { weight: 6, dimensions: { width: 10, height: 10,
 *             depth: 1 }, },
 * 
 *             pricing: { list: 1200, retail: 1100, savings: 100, pct_savings: 8
 *             },
 * 
 *             details: { title: "A Love Supreme [Original Recording Reissued]",
 *             artist: "John Coltrane", genre: [ "Jazz", "General" ], tracks: [
 *             "A Love Supreme Part I: Acknowledgement",
 *             "A Love Supreme Part II - Resolution",
 *             "A Love Supreme, Part III: Pursuance",
 *             "A Love Supreme, Part IV-Psalm" ], }, }
 */
public class ProductCategoryBean {
	private String sku;
	private String type; //品类
	private String title;
	private String description;
	private String asin;
	private Shipping shipping;
	private Pricing pricing;
	private Details details;
	
	public ProductCategoryBean() {
		super();
	}

	public ProductCategoryBean(String sku, String type, String title,
			String description, String asin, Shipping shipping,
			Pricing pricing, Details details) {
		super();
		this.sku = sku;
		this.type = type;
		this.title = title;
		this.description = description;
		this.asin = asin;
		this.shipping = shipping;
		this.pricing = pricing;
		this.details = details;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public Pricing getPricing() {
		return pricing;
	}

	public void setPricing(Pricing pricing) {
		this.pricing = pricing;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ProductCategoryBean [description=" + description + ", details="
				+ details + ", pricing=" + pricing + ", shipping=" + shipping
				+ ", sku=" + sku + ", title=" + title + ", type=" + type + "]";
	}

}
