package me.ybleeho.model;

import java.util.Date;

public class Artical {

	private int articalId;
	private String title;
	private String content;
	private Date publishDate;
	private String author;
	private int typeId=-1;
	private String typeName;
	private int click;
	private int isHead;
	private int isImage;
	private String imageName;
	private int isHot;
	
	public Artical(int articalId, String title) {
		super();
		this.articalId = articalId;
		this.title = title;
	}
	public Artical() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getArticalId() {
		return articalId;
	}
	public void setArticalId(int articalId) {
		this.articalId = articalId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getClick() {
		return click;
	}
	public void setClick(int click) {
		this.click = click;
	}
	public int getIsHead() {
		return isHead;
	}
	public void setIsHead(int isHead) {
		this.isHead = isHead;
	}
	public int getIsImage() {
		return isImage;
	}
	public void setIsImage(int isImage) {
		this.isImage = isImage;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public int getIsHot() {
		return isHot;
	}
	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}
	
	
}
