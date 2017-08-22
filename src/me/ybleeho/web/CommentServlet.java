package me.ybleeho.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import me.ybleeho.dao.CommentDao;
import me.ybleeho.model.Comment;
import me.ybleeho.model.PageBean;
import me.ybleeho.util.DbUtil;
import me.ybleeho.util.NavUtil;
import me.ybleeho.util.PageUtil;
import me.ybleeho.util.PropertiesUtil;
import me.ybleeho.util.StringUtil;
import me.ybleeho.util.ResponseUtil;

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
		}else if("backList".equals(action)){
			commentBackList(request,response);
		}else if("delete".equals(action)){
			commentDelete(request,response);
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
	
	private void commentBackList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s_bCommentDate=request.getParameter("s_bCommentDate");
		String s_aCommentDate=request.getParameter("s_aCommentDate");
		String page=request.getParameter("page");
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
			session.setAttribute("s_bCommentDate", s_bCommentDate);
			session.setAttribute("s_aCommentDate", s_aCommentDate);
		}else{
			s_bCommentDate=(String) session.getAttribute("s_bCommentDate");
			s_aCommentDate=(String) session.getAttribute("s_aCommentDate");
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int total=commentDao.commentCount(con, new Comment(), s_bCommentDate, s_aCommentDate);
			String pageCode=PageUtil.getPagation(request.getContextPath()+"/comment?action=backList", total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("backPageSize")));
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("backPageSize")));
			List<Comment> commentBackList=commentDao.commentList(con, new Comment(),pageBean,s_bCommentDate,s_aCommentDate);
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("commentBackList", commentBackList);
			request.setAttribute("navCode", NavUtil.genArticalManageNavigation("문장댓글관리", "문장댓글점검"));
			request.setAttribute("mainPage", "/background/comment/commentList.jsp");
			request.getRequestDispatcher("/background/mainTemp.jsp").forward(request, response);
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
	
	private void commentDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commentIds=request.getParameter("commentIds");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=commentDao.commentDelete(con, commentIds);
			if(delNums>0){
				result.put("success", true);
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "삭제실패");
			}
			ResponseUtil.write(result, response);
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
