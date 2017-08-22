package me.ybleeho.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import me.ybleeho.dao.CommentDao;
import me.ybleeho.dao.ArticalDao;
import me.ybleeho.dao.ArticalTypeDao;
import me.ybleeho.model.Artical;
import me.ybleeho.model.ArticalType;
import me.ybleeho.model.PageBean;
import me.ybleeho.model.Comment;
import me.ybleeho.util.DbUtil;
import me.ybleeho.util.DateUtil;
import me.ybleeho.util.NavUtil;
import me.ybleeho.util.PageUtil;
import me.ybleeho.util.PropertiesUtil;
import me.ybleeho.util.StringUtil;
import me.ybleeho.util.ResponseUtil;

public class ArticalServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	ArticalDao articalDao=new ArticalDao();
	ArticalTypeDao articalTypeDao=new ArticalTypeDao();
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
		if("list".equals(action)){
			this.articalList(request, response);
		}else if("show".equals(action)){
			this.articalShow(request, response);
		}else if("preSave".equals(action)){
			this.articalPreSave(request, response);
		}else if("save".equals(action)){
			this.articalSave(request, response);
		}else if("backList".equals(action)){
			this.articalBackList(request,response);
		}else if("delete".equals(action)){
			this.articalDelete(request,response);
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
			int total=articalDao.articalCount(con, s_artical,null,null);
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			List<Artical> newestArticalListWithType=articalDao.articalList(con, s_artical, pageBean,null,null);
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
			Comment s_comment=new Comment();
			s_comment.setArticalId(Integer.parseInt(articalId));
			List<Comment> commentList=commentDao.commentList(con, s_comment,null,null,null);
			request.setAttribute("commentList", commentList);
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
	
	
	private void articalPreSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String articalId=request.getParameter("articalId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			if(StringUtil.isNotEmpty(articalId)){
				Artical artical=articalDao.getArticalById(con, articalId);
				request.setAttribute("artical", artical);
			}
			List<ArticalType> articalTypeList=articalTypeDao.articalTypeList(con);
			request.setAttribute("articalTypeList", articalTypeList);
			if(StringUtil.isNotEmpty(articalId)){
				request.setAttribute("navCode", NavUtil.genArticalManageNavigation("문장관리", "문장변경"));				
			}else{
				request.setAttribute("navCode", NavUtil.genArticalManageNavigation("문장관리", "문장추가"));				
			}
			request.setAttribute("mainPage", "/background/artical/articalSave.jsp");
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
	
	private void articalSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		List<FileItem> items=null;
		try {
			items=upload.parseRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator itr=items.iterator();
		Artical artical=new Artical();
		while(itr.hasNext()){
			FileItem item=(FileItem) itr.next();
			if(item.isFormField()){
				String fieldName=item.getFieldName();
				if("articalId".equals(fieldName)){
					if(StringUtil.isNotEmpty(item.getString("utf-8"))){
						artical.setArticalId(Integer.parseInt(item.getString("utf-8")));
					}
				}
				if("title".equals(fieldName)){
					artical.setTitle(item.getString("utf-8"));
				}
				if("content".equals(fieldName)){
					artical.setContent(item.getString("utf-8"));
				}
				if("author".equals(fieldName)){
					artical.setAuthor(item.getString("utf-8"));
				}
				if("typeId".equals(fieldName)){
					artical.setTypeId(Integer.parseInt(item.getString("utf-8")));
				}
				if("isHead".equals(fieldName)){
					artical.setIsHead(Integer.parseInt(item.getString("utf-8")));
				}
				if("isImage".equals(fieldName)){
					artical.setIsImage(Integer.parseInt(item.getString("utf-8")));
				}
				if("isHot".equals(fieldName)){
					artical.setIsHot(Integer.parseInt(item.getString("utf-8")));
				}
				if("imageName".equals(fieldName)&&artical.getImageName()==null){
					if(StringUtil.isNotEmpty(item.getString("utf-8"))){
						artical.setImageName(item.getString("utf-8").split("/")[1]);
					}
				}
			}else if(!"".equals(item.getName())){
				try {
					String imageName=DateUtil.getCurrentDateStr();
					artical.setImageName(imageName+"."+item.getName().split("\\.")[1]);
					String filePath=PropertiesUtil.getValue("imagePath")+imageName+"."+item.getName().split("\\.")[1];
					item.write(new File(filePath));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		Connection con=null;
		try{
			con=dbUtil.getCon();
			if(artical.getArticalId()!=0){
				articalDao.articalUpdate(con, artical);
			}else{
				articalDao.articalAdd(con, artical);
			}
			request.getRequestDispatcher("/artical?action=backList").forward(request, response);
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
	
	private void articalBackList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s_bPublishDate=request.getParameter("s_bPublishDate");
		String s_aPublishDate=request.getParameter("s_aPublishDate");
		String s_title=request.getParameter("s_title");
		String page=request.getParameter("page");
		HttpSession session=request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
			session.setAttribute("s_bPublishDate", s_bPublishDate);
			session.setAttribute("s_aPublishDate", s_aPublishDate);
			session.setAttribute("s_title", s_title);
		}else{
			s_bPublishDate=(String) session.getAttribute("s_bPublishDate");
			s_aPublishDate=(String) session.getAttribute("s_aPublishDate");
			s_title=(String) session.getAttribute("s_title");
		}
		Connection con=null;
		Artical s_artical=new Artical();
		if(StringUtil.isNotEmpty(s_title)){
			s_artical.setTitle(s_title);
		}
		try{
			con=dbUtil.getCon();
			int total=articalDao.articalCount(con, s_artical, s_bPublishDate, s_aPublishDate);
			String pageCode=PageUtil.getPagation(request.getContextPath()+"/artical?action=backList", total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("backPageSize")));
			PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("backPageSize")));
			List<Artical> articalBackList=articalDao.articalList(con, s_artical,pageBean,s_bPublishDate,s_aPublishDate);
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("articalBackList", articalBackList);
			request.setAttribute("navCode", NavUtil.genArticalManageNavigation("문장관리", "문장점검"));
			request.setAttribute("mainPage", "/background/artical/articalList.jsp");
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
	
	private void articalDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String articalId=request.getParameter("articalId");
		Connection con=null;
		boolean delFlag;
		try{
			con=dbUtil.getCon();
			int delNums=articalDao.articalDelete(con, articalId);
			if(delNums==1){
				delFlag=true;
			}else{
				delFlag=false;
			}
			ResponseUtil.write(delFlag, response);
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
