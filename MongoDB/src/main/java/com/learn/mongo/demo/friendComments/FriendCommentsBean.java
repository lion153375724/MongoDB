package com.learn.mongo.demo.friendComments;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.learn.mongo.demo.AutoIncKey;

@Document(collection = "friendComment") 
public class FriendCommentsBean {
	
	@AutoIncKey  
    @Id  
    private Long id = 0L;  
	
	private String uid;
	private List<PraiseBean> praiseList; //点赞列表
	private List<CommentBean> commentList; //评论列表
	
	public FriendCommentsBean(String uid,
			List<PraiseBean> praiseList, List<CommentBean> commentList) {
		super();
		this.uid = uid;
		this.praiseList = praiseList;
		this.commentList = commentList;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<PraiseBean> getPraiseList() {
		return praiseList;
	}
	public void setPraiseList(List<PraiseBean> praiseList) {
		this.praiseList = praiseList;
	}
	public List<CommentBean> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<CommentBean> commentList) {
		this.commentList = commentList;
	}
	
	
}
