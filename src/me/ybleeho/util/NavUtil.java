package me.ybleeho.util;

public class NavUtil {

	public static String genArticalListNavigation(String typeName,String typeId){
		StringBuffer navCode=new StringBuffer();
		navCode.append("지금위치：&nbsp;&nbsp;");
		navCode.append("<a href='goIndex'>처음으로</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='artical?action=list&typeId="+typeId+"'>"+typeName+"</a>");
		return navCode.toString();
	}
	
	public static String genArticalNavigation(String typeName,String typeId,String articalName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("지금위치：&nbsp;&nbsp;");
		navCode.append("<a href='goIndex'>처음으로</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='artical?action=list&typeId="+typeId+"'>"+typeName+"</a>&nbsp;&nbsp;>&nbsp;&nbsp;"+articalName);
		return navCode.toString();
	}
	
	public static String genArticalManageNavigation(String modName,String actionName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("지금위치：&nbsp;&nbsp;");
		navCode.append("홈페이지&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(modName+"&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(actionName+"&nbsp;&nbsp;");
		return navCode.toString();
	}
}
