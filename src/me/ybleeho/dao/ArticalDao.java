package me.ybleeho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import me.ybleeho.model.Artical;
import me.ybleeho.util.DateUtil;
import me.ybleeho.util.PropertiesUtil;
import me.ybleeho.model.PageBean;

public class ArticalDao {
	public List<Artical> articalList(Connection con,String sql)throws Exception{
		List<Artical> articalList=new ArrayList<Artical>();
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Artical artical=new Artical();
			artical.setArticalId(rs.getInt("articalId"));
			artical.setTitle(rs.getString("title"));
			artical.setContent(rs.getString("content"));
			artical.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			artical.setAuthor(rs.getString("author"));
			artical.setTypeId(rs.getInt("typeId"));
			artical.setClick(rs.getInt("click"));
			artical.setIsHead(rs.getInt("isHead"));
			artical.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			artical.setIsHot(rs.getInt("isHot"));
			articalList.add(artical);
		}
		return articalList;
	}
	public List<Artical> articalList(Connection con,Artical s_artical,PageBean pageBean)throws Exception{
		List<Artical> articalList=new ArrayList<Artical>();
		StringBuffer sb=new StringBuffer("select * from t_artical t1,t_articalType t2 where t1.typeId=t2.articalTypeId ");
		if(s_artical.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_artical.getTypeId());
		}
		sb.append(" order by t1.publishDate desc ");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Artical artical=new Artical();
			artical.setArticalId(rs.getInt("articalId"));
			artical.setTitle(rs.getString("title"));
			artical.setContent(rs.getString("content"));
			artical.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			artical.setAuthor(rs.getString("author"));
			artical.setTypeId(rs.getInt("typeId"));
			artical.setClick(rs.getInt("click"));
			artical.setIsHead(rs.getInt("isHead"));
			artical.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			artical.setIsHot(rs.getInt("isHot"));
			articalList.add(artical);
		}
		return articalList;
	}
	
	public int articalCount(Connection con,Artical s_artical)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_artical");
		if(s_artical.getTypeId()!=-1){
			sb.append(" and typeId="+s_artical.getTypeId());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	public Artical getArticalById(Connection con,String articalId)throws Exception{
		String sql="select * from t_artical t1,t_articalType t2 where t1.typeId=t2.articalTypeId and t1.articalId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, articalId);
		ResultSet rs=pstmt.executeQuery();
		Artical artical=new Artical();
		if(rs.next()){
			artical.setArticalId(rs.getInt("articalId"));
			artical.setTitle(rs.getString("title"));
			artical.setContent(rs.getString("content"));
			artical.setPublishDate(DateUtil.formatString(rs.getString("publishDate"), "yyyy-MM-dd HH:mm:ss"));
			artical.setAuthor(rs.getString("author"));
			artical.setTypeName(rs.getString("typeName"));
			artical.setTypeId(rs.getInt("typeId"));
			artical.setClick(rs.getInt("click"));
			artical.setIsHead(rs.getInt("isHead"));
			artical.setImageName(PropertiesUtil.getValue("userImage")+rs.getString("imageName"));
			artical.setIsHot(rs.getInt("isHot"));
		}
		return artical;
	}
	
	public int articalClick(Connection con,String articalId)throws Exception{
		String sql="update t_artical set click=click+1 where articalId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, articalId);
		return pstmt.executeUpdate();
	}
	
	public List<Artical> getUpAndDownPageId(Connection con,String articalId)throws Exception{
		List<Artical> upAndDownPage=new ArrayList<Artical>();
		String sql="SELECT * FROM t_artical WHERE articalId<? ORDER BY articalId DESC LIMIT 1;";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, articalId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			upAndDownPage.add(new Artical(rs.getInt("articalId"),rs.getString("title")));
		}else{
			upAndDownPage.add(new Artical(-1,""));
		}
		
		sql="SELECT * FROM t_artical WHERE articalId>? ORDER BY articalId ASC LIMIT 1;";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, articalId);
		rs=pstmt.executeQuery();
		if(rs.next()){
			upAndDownPage.add(new Artical(rs.getInt("articalId"),rs.getString("title")));
		}else{
			upAndDownPage.add(new Artical(-1,""));
		}
		return upAndDownPage;
	}
}
