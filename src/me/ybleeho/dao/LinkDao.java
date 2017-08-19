package me.ybleeho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import me.ybleeho.model.Link;

public class LinkDao {
	public List<Link> linkList(Connection con,String sql)throws Exception{
		List<Link> linkList=new ArrayList<Link>();
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Link link=new Link();
			link.setLinkId(rs.getInt("linkId"));
			link.setLinkName(rs.getString("linkName"));
			link.setLinkUrl(rs.getString("linkUrl"));
			link.setLinkEmail(rs.getString("linkUrl"));
			link.setOrderNum(rs.getInt("orderNum"));
			linkList.add(link);
		}
		return linkList;
	}
}
