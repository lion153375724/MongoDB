package com.learn.mongo.demo.productCategory;

import java.util.Date;

public class Details {

	private String title;
	private String artist;
	private String[] genre; //风格标签
	private String[] tracks; //跟踪
	private String[] director; //作者
	private String[] actor; //演员
	private String aspect_ration;
	private Date issue_date; //发行日期

	public Details() {
		super();
	}

	public Details(String title, String artist, String[] genre,
			String[] tracks, String[] director, String[] actor,
			String aspect_ration, Date issue_date) {
		super();
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.tracks = tracks;
		this.director = director;
		this.actor = actor;
		this.aspect_ration = aspect_ration;
		this.issue_date = issue_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String[] getGenre() {
		return genre;
	}

	public void setGenre(String[] genre) {
		this.genre = genre;
	}

	public String[] getTracks() {
		return tracks;
	}

	public void setTracks(String[] tracks) {
		this.tracks = tracks;
	}

	public String[] getDirector() {
		return director;
	}

	public void setDirector(String[] director) {
		this.director = director;
	}

	public String getAspect_ration() {
		return aspect_ration;
	}

	public void setAspect_ration(String aspect_ration) {
		this.aspect_ration = aspect_ration;
	}

	public String[] getActor() {
		return actor;
	}

	public void setActor(String[] actor) {
		this.actor = actor;
	}

	public Date getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}

}
