package me.ybleeho.model;

public class ArticalType {

	private int articalTypeId;
	private String typeName;
	
	
	public ArticalType() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ArticalType(String typeName) {
		super();
		this.typeName = typeName;
	}
	public int getArticalTypeId() {
		return articalTypeId;
	}
	public void setArticalTypeId(int articalTypeId) {
		this.articalTypeId = articalTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
