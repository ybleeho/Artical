package me.ybleeho.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ybleeho.dao.CommentDao;
import me.ybleeho.model.Comment;
import me.ybleeho.model.Artical;
import me.ybleeho.model.PageBean;
import me.ybleeho.util.DbUtil;
import me.ybleeho.util.NavUtil;
import me.ybleeho.util.PageUtil;
import me.ybleeho.util.PropertiesUtil;
import me.ybleeho.util.StringUtil;

public class CommentServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	CommentDao commentDao=new CommentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("save".equals(action)){
			commentSave(request,response);
		}
		
	}
	
	private void commentSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String articalId=request.getParameter("articalId");
		String content=request.getParameter("content");
		
		String userIP=request.getRemoteAddr();
		Comment comment=new Comment(Integer.parseInt(articalId), content, userIP);
		Connection con=null;
		try{
			con=dbUtil.getCon();
			commentDao.commentAdd(con, comment);
			request.getRequestDispatcher("artical?action=show&articalId="+articalId).forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
