package me.ybleeho.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import me.ybleeho.dao.LinkDao;
import me.ybleeho.model.Link;
import me.ybleeho.util.DbUtil;
import me.ybleeho.util.NavUtil;
import me.ybleeho.util.StringUtil;
import me.ybleeho.util.ResponseUtil;

public class LinkServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	LinkDao linkDao=new LinkDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("preSave".equals(action)){
			linkPreSave(request,response);
		}else if("save".equals(action)){
			linkSave(request,response);
		}else if("backList".equals(action)){
			linkBackList(request,response);
		}else if("delete".equals(action)){
			linkDelete(request,response);
		}
		
	}
	
	private void linkDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String linkId=request.getParameter("linkId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int delNums=linkDao.linkDelete(con, linkId);
			if(delNums>0){
				result.put("success", true);
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

	private void linkBackList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		Connection con=null;
		try{
			con=dbUtil.getCon();
			List<Link> linkBackList=linkDao.linkList(con, "select * from t_link order by orderNum");
			request.setAttribute("linkBackList", linkBackList);
			request.setAttribute("navCode", NavUtil.genArticalManageNavigation("핫링크관리", "핫링크점검"));
			request.setAttribute("mainPage", "/background/link/linkList.jsp");
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
	
	private void linkPreSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String linkId=request.getParameter("linkId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			
			if(StringUtil.isNotEmpty(linkId)){
				Link link=linkDao.getLinkById(con, linkId);
				request.setAttribute("link", link);
			}
			if(StringUtil.isNotEmpty(linkId)){
				request.setAttribute("navCode", NavUtil.genArticalManageNavigation("핫링크관리", "핫링크변겅"));
			}else{
				request.setAttribute("navCode", NavUtil.genArticalManageNavigation("핫링크관리", "핫링크추가"));
			}
			request.setAttribute("mainPage", "/background/link/linkSave.jsp");
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
	
	private void linkSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		String linkId=request.getParameter("linkId");
		String linkName=request.getParameter("linkName");
		String linkUrl=request.getParameter("linkUrl");
		String linkEmail=request.getParameter("linkEmail");
		String orderNum=request.getParameter("orderNum");
		
		Link link=new Link(linkName, linkUrl, linkEmail, Integer.parseInt(orderNum));
		
		if(StringUtil.isNotEmpty(linkId)){
			link.setLinkId(Integer.parseInt(linkId));
		}
		try{
			con=dbUtil.getCon();
			if(StringUtil.isNotEmpty(linkId)){
				linkDao.linkUpdate(con, link);
			}else{
				linkDao.linkAdd(con, link);
			}
			request.getRequestDispatcher("/link?action=backList").forward(request, response);
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
