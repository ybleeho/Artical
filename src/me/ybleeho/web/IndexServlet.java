package me.ybleeho.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ybleeho.dao.LinkDao;
import me.ybleeho.dao.ArticalTypeDao;
import me.ybleeho.dao.ArticalDao;
import me.ybleeho.model.ArticalType;
import me.ybleeho.model.Artical;
import me.ybleeho.model.Link;
import me.ybleeho.util.DbUtil;
import me.ybleeho.util.StringUtil;

public class IndexServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	ArticalDao articalDao=new ArticalDao();
	ArticalTypeDao articalTypeDao=new ArticalTypeDao();
	LinkDao linkDao=new LinkDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		Connection con=null;
		try{
			con=dbUtil.getCon();
			
			List<ArticalType> articalTypeList=articalTypeDao.articalTypeList(con);
			
			String sql="select * from t_artical where isImage=1 order by publishDate desc limit 0,5";
			List<Artical> imageArticalList=articalDao.articalList(con, sql);
			request.setAttribute("imageArticalList", imageArticalList);
			
			sql="select * from t_artical where isHead=1 order by publishDate desc limit 0,1";
			List<Artical> headArticalList=articalDao.articalList(con, sql);
			Artical headArtical=headArticalList.get(0);
			headArtical.setContent(StringUtil.Html2Text(headArtical.getContent()));
			request.setAttribute("headArtical", headArtical);
			
			
			sql="select * from t_artical where isHot=1 order by publishDate desc limit 0,8 ";
			List<Artical> hotSpotArticalList=articalDao.articalList(con, sql);
			request.setAttribute("hotSpotArticalList", hotSpotArticalList);
			
			List allIndexArticalList=new ArrayList();
			if(articalTypeList!=null && articalTypeList.size()!=0){
				for(int i=0;i<articalTypeList.size();i++){
					ArticalType articalType=articalTypeList.get(i);
					sql="select * from t_artical,t_articalType where typeId=articalTypeId and typeId="+articalType.getArticalTypeId()+" order by publishDate desc limit 0,8";
					List<Artical> oneSubList=articalDao.articalList(con, sql);
					allIndexArticalList.add(oneSubList);
				}
			}
			request.setAttribute("allIndexArticalList", allIndexArticalList);
			
			sql="select * from t_link order by orderNum ";
			List<Link> linkList=linkDao.linkList(con, sql);
			request.setAttribute("linkList", linkList);
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
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

