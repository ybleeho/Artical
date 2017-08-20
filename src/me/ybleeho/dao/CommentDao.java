package me.ybleeho.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import me.ybleeho.model.Comment;
import me.ybleeho.util.DateUtil;

public class CommentDao {
	
	public List<Comment> commentList(Connection con,Comment s_comment)throws Exception{
		List<Comment> commentList=new ArrayList<Comment>();
		StringBuffer sb=new StringBuffer("select * from t_comment");
		if(s_comment.getArticalId()!=-1){
			sb.append(" and articalId="+s_comment.getArticalId());
		}
		sb.append(" order by commentDate desc ");
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Comment comment=new Comment();
			comment.setCommentId(rs.getInt("commentId"));
			comment.setArticalId(rs.getInt("articalId"));
			comment.setContent(rs.getString("content"));
			comment.setUserIP(rs.getString("userIP"));
			comment.setCommentDate(DateUtil.formatString(rs.getString("commentDate"), "yyyy-MM-dd HH:mm:ss"));
			commentList.add(comment);
		}
		return commentList;
	}
	
	public int commentAdd(Connection con,Comment comment)throws Exception{
		String sql="insert into t_comment values(null,?,?,?,now())";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, comment.getArticalId());
		pstmt.setString(2, comment.getContent());
		pstmt.setString(3, comment.getUserIP());
		return pstmt.executeUpdate();
	}
}
