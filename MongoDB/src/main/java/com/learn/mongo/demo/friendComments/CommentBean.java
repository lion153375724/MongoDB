package com.learn.mongo.demo.friendComments;

/**
 * @author jason
 * @createTime 2017年10月31日下午4:34:29
 */
public class CommentBean {
	private String uid;
	private String headUrl;
	private String msg;
	
	
	public CommentBean(String uid, String headUrl, String msg) {
		super();
		this.uid = uid;
		this.headUrl = headUrl;
		this.msg = msg;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
