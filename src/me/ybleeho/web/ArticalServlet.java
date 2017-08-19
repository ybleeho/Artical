package me.ybleeho.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ybleeho.dao.ArticalDao;
import me.ybleeho.dao.ArticalTypeDao;
import me.ybleeho.model.Artical;
import me.ybleeho.model.PageBean;
import me.ybleeho.util.DbUtil;
import me.ybleeho.util.NavUtil;
import me.ybleeho.util.PageUtil;
import me.ybleeho.util.PropertiesUtil;
import me.ybleeho.util.StringUtil;

public class ArticalServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	ArticalDao articalDao=new ArticalDao();
	ArticalTypeDao articalTypeDao=new ArticalTypeDao();
	
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
		if("list".equals(action)){
			this.articalList(request, response);
		}else if("show".equals(action)){
			this.articalShow(request, response);
		}
		
	}

	private void articalList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String typeId=request.getParameter("typeId");
		String page=request.getParameter("page");
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		Connection con=null;
		Artical s_artical=new Artical();
		if(StringUtil.isNotEmpty(typeId)){
			s_artical.setTypeId(Integer.parseInt(typeId));
		}
		try{
			con=dbUtil.getCon();
			int total=articalDao.articalCount(con, s_artical);
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			List<Artical> newestArticalListWithType=articalDao.articalList(con, s_artical, pageBean);
			request.setAttribute("newestArticalListWithType", newestArticalListWithType);
			request.setAttribute("navCode", NavUtil.genArticalListNavigation(articalTypeDao.getArticalTypeById(con, typeId).getTypeName(), typeId));
			request.setAttribute("pageCode", PageUtil.getUpAndDownPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")), typeId));
			request.setAttribute("mainPage", "artical/articalList.jsp");
			request.getRequestDispatcher("foreground/articalTemp.jsp").forward(request, response);
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
	
	private void articalShow(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String articalId=request.getParameter("articalId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			articalDao.articalClick(con, articalId);
			Artical artical=articalDao.getArticalById(con, articalId);
			request.setAttribute("artical", artical);
			request.setAttribute("pageCode", this.genUpAndDownPageCode(articalDao.getUpAndDownPageId(con, articalId)));
			request.setAttribute("navCode", NavUtil.genArticalNavigation(artical.getTypeName(), artical.getTypeId()+"",artical.getTitle()));
			request.setAttribute("mainPage", "artical/articalShow.jsp");
			request.getRequestDispatcher("foreground/articalTemp.jsp").forward(request, response);
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
	
	private String genUpAndDownPageCode(List<Artical> upAndDownPage){
		Artical upArtical=upAndDownPage.get(0);
		Artical downArtical=upAndDownPage.get(1);
		StringBuffer pageCode=new StringBuffer();
		if(upArtical.getArticalId()==-1){
			pageCode.append("<p>이전글：-</p>");
		}else{
			pageCode.append("<p>이전글：<a href='artical?action=show&articalId="+upArtical.getArticalId()+"'>"+upArtical.getTitle()+"</a></p>");
		}
		if(downArtical.getArticalId()==-1){
			pageCode.append("<p>다음글：-</p>");
		}else{
			pageCode.append("<p>다음글：<a href='artical?action=show&articalId="+downArtical.getArticalId()+"'>"+downArtical.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
}
