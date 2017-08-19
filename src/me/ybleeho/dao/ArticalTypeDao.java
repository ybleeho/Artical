package me.ybleeho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import me.ybleeho.model.ArticalType;

public class ArticalTypeDao {
	public List<ArticalType> articalTypeList(Connection con)throws Exception{
		List<ArticalType> articalTypeList=new ArrayList<ArticalType>();
		String sql="select * from t_articalType";
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			ArticalType articalType=new ArticalType();
			articalType.setArticalTypeId(rs.getInt("articalTypeId"));
			articalType.setTypeName(rs.getString("typeName"));
			articalTypeList.add(articalType);
		}
		return articalTypeList;
	}
	public ArticalType getArticalTypeById(Connection con,String articalTypeId)throws Exception{
		ArticalType articalType=new ArticalType();
		String sql="select * from t_articalType where articalTypeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, articalTypeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			articalType.setArticalTypeId(rs.getInt("articalTypeId"));
			articalType.setTypeName(rs.getString("typeName"));
		}
		return articalType;
	}
}
