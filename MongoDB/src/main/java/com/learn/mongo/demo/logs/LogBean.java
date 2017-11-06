package com.learn.mongo.demo.logs;

import java.util.Date;

/**
 * 
 * @author jason
 * @createTime 2017年10月25日下午4:46:44
 * 
 *             一行日志最终存储的内容可能类似如下: host: "127.0.0.1", time:
 *             ISODate("2000-10-10T20:55:36Z"), path: "/apache_pb.gif", referer:
 *             "[http://www.example.com/start.html](http://www.example.com/start.html)"
 *             , user_agent: "Mozilla/4.08 [en] (Win98; I ;Nav)"
 */
public class LogBean {

	private String host;
	private Date time;
	private String path;
	private String referer;
	private String user_agent;

	public LogBean(String host, Date time, String path, String referer,
			String user_agent) {
		super();
		this.host = host;
		this.time = time;
		this.path = path;
		this.referer = referer;
		this.user_agent = user_agent;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

}
