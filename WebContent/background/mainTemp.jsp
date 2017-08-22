<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/style/artical.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>

<script src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<%
	String mainPage=(String)request.getAttribute("mainPage");
	if(mainPage==null || mainPage.equals("")){
		mainPage="/background/default.jsp";
	}
%>
</head>
<script type="text/javascript">
	function refreshSystem(){
		$.post("init",{},
			function(flag){
				var flag=eval('('+flag+')');
				if(flag){
					alert("갱신성공！");
				}else{
					alert("갱신실패！");
				}
			}
		);
	}
</script>
<body>
<div class="container">
<jsp:include page="/background/common/head.jsp"/>

<div class="row-fluid">
	<div class="span3">
		<div class="newsMenu">
			<ul class="nav nav-tabs nav-stacked">
				  <li><a href=""><strong>홈 페이지</strong></a></li>
				  <li><a href=""><strong>문장관리</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/artical?action=preSave">&nbsp;&nbsp;문장추가</a></li>
				  <li><a href="${pageContext.request.contextPath}/artical?action=backList">&nbsp;&nbsp;문장점검</a></li>
				  <li><a href=""><strong>댓글관리</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/comment?action=backList">&nbsp;&nbsp;댓글점검</a></li>
				  <li><a href=""><strong>문장종류관리</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/articalType?action=preSave">&nbsp;&nbsp;문장종류추가</a></li>
				  <li><a href="${pageContext.request.contextPath}/articalType?action=backList">&nbsp;&nbsp;문장종류점검</a></li>
				  <li><a href=""><strong>핫링크관리</strong></a></li>
				  <li><a href="${pageContext.request.contextPath}/link?action=preSave">&nbsp;&nbsp;핫링크추가</a></li>
				  <li><a href="${pageContext.request.contextPath}/link?action=backList">&nbsp;&nbsp;핫링크점검</a></li>
				  <li><a href=""><strong>시스템관리</strong></a></li>
				  <li><a href="javascript:refreshSystem()">&nbsp;&nbsp;서버캐시갱신</a></li>
			</ul>
		</div>
	</div>
	<div class="span9">
		<jsp:include page="<%=mainPage %>"></jsp:include>
	</div>
</div>
<jsp:include page="/background/common/foot.jsp"/>
</div>
</body>
</html>