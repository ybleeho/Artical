package me.ybleeho.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import me.ybleeho.dao.ArticalDao;
import me.ybleeho.dao.ArticalTypeDao;
import me.ybleeho.model.Link;
import me.ybleeho.model.ArticalType;
import me.ybleeho.util.DbUtil;
import me.ybleeho.util.NavUtil;
import me.ybleeho.util.ResponseUtil;
import me.ybleeho.util.StringUtil;

public class ArticalTypeServlet extends HttpServlet{

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
		if("preSave".equals(action)){
			this.articalTypePreSave(request, response);
		}else if("save".equals(action)){
			this.articalTypeSave(request, response);
		}else if("backList".equals(action)){
			this.articalTypeBackList(request, response);
		}else if("delete".equals(action)){
			this.articalTypeDelete(request, response);
		}
		
	}

	
	private void articalTypePreSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String articalTypeId=request.getParameter("articalTypeId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			if(StringUtil.isNotEmpty(articalTypeId)){
				ArticalType articalType=articalTypeDao.getArticalTypeById(con, articalTypeId);
				request.setAttribute("articalType", articalType);
			}
			
			if(StringUtil.isNotEmpty(articalTypeId)){
				request.setAttribute("navCode", NavUtil.genArticalManageNavigation("문장종류관리", "문장종류변경"));
			}else{
				request.setAttribute("navCode", NavUtil.genArticalManageNavigation("문장종류관리", "문장종류추가"));
			}
			request.setAttribute("mainPage", "/background/articalType/articalTypeSave.jsp");
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
	
	private void articalTypeSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		String articalTypeId=request.getParameter("articalTypeId");
		String typeName=request.getParameter("typeName");
		
		ArticalType articalType=new ArticalType(typeName);
		
		if(StringUtil.isNotEmpty(articalTypeId)){
			articalType.setArticalTypeId(Integer.parseInt(articalTypeId));
		}
		try{
			con=dbUtil.getCon();
			if(StringUtil.isNotEmpty(articalTypeId)){
				articalTypeDao.articalTypeUpdate(con, articalType);
			}else{
				articalTypeDao.articalTypeAdd(con, articalType);
			}
			request.getRequestDispatcher("/articalType?action=backList").forward(request, response);
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
	
	
	private void articalTypeBackList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		try{
			con=dbUtil.getCon();
			List<ArticalType> articalTypeBackList=articalTypeDao.articalTypeList(con);
			request.setAttribute("articalTypeBackList", articalTypeBackList);
			request.setAttribute("navCode", NavUtil.genArticalManageNavigation("문장종류관리", "문장종류점검"));
			request.setAttribute("mainPage", "/background/articalType/articalTypeList.jsp");
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
	
	private void articalTypeDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String articalTypeId=request.getParameter("articalTypeId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			boolean exist=articalDao.existArticalWithArticalTypeId(con, articalTypeId);
			if(exist){
				result.put("errorMsg", "이 문장종류의 문장이 존재하기에 삭제할수 없습니다");
			}else{
				int delNums=articalTypeDao.articalTypeDelete(con, articalTypeId);
				if(delNums>0){
					result.put("success", true);
				}else{
					result.put("errorMsg", "삭제실패");
				}
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
