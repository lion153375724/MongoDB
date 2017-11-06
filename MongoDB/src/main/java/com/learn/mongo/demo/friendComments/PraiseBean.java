package com.learn.mongo.demo.friendComments;

public class PraiseBean {
	private String uid;
	private String headUrl;
	
	
	public PraiseBean(String uid, String headUrl) {
		super();
		this.uid = uid;
		this.headUrl = headUrl;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	
	
}
