package me.ybleeho.model;

import java.util.Date;

public class Comment {
	
	private int commentId;
	private int articalId=-1;
	private String content;
	private String userIP;
	private Date commentDate;
	private String articalTitle;
	
	
	public String getArticalTitle() {
		return articalTitle;
	}
	public void setArticalTitle(String articalTitle) {
		this.articalTitle = articalTitle;
	}
	public Comment(int articalId, String content, String userIP){
		super();
		this.articalId = articalId;
		this.content = content;
		this.userIP = userIP;
	}
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getArticalId() {
		return articalId;
	}
	public void setArticalId(int articalId) {
		this.articalId = articalId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
}
