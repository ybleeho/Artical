package me.ybleeho.web;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import me.ybleeho.dao.ArticalDao;
import me.ybleeho.dao.ArticalTypeDao;
import me.ybleeho.model.Artical;
import me.ybleeho.model.ArticalType;
import me.ybleeho.util.DbUtil;

public class InitServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	ArticalDao articalDao=new ArticalDao();
	ArticalTypeDao articalTypeDao=new ArticalTypeDao();

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext application=config.getServletContext();
		this.refreshSystem(application);
	}
	
	private void refreshSystem(ServletContext application){
		Connection con=null;
		try{
			con=dbUtil.getCon();
			
			List<ArticalType> articalTypeList=articalTypeDao.articalTypeList(con);
			application.setAttribute("articalTypeList", articalTypeList);
			
			String sql="select * from t_artical order by publishDate desc limit 0,8 ";
			List<Artical> newestArticalList=articalDao.articalList(con, sql);
			application.setAttribute("newestArticalList", newestArticalList);
			
			sql="select * from t_artical order by click desc limit 0,8";
			List<Artical> hotArticalList=articalDao.articalList(con, sql);
			application.setAttribute("hotArticalList", hotArticalList);
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
