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
</head>
<body>
<div class="container">
<jsp:include page="/foreground/common/head.jsp"/>

<div class="row-fluid">
	<div class="span8">
		<jsp:include page="${mainPage }"/>
	</div>
	<div class="span4">
		<div class="data_list right_artical_list">
			<div class="dataHeader">New</div>
			<div class="datas">
				<ul>
					<c:forEach var="newestArtical" items="${newestArticalList }">
						<li><a href="artical?action=show&articalId=${newestArtical.articalId }" title="${newestArtical.title }">${fn:substring(newestArtical.title,0,22) }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="data_list right_artical_list">
			<div class="dataHeader">Hot</div>
			<div class="datas">
				<ul>
					<c:forEach var="hotArtical" items="${hotArticalList }">
						<li><a href="artical?action=show&articalId=${hotArtical.articalId }" title="${hotArtical.title }">${fn:substring(hotArtical.title,0,22) }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>

<jsp:include page="/foreground/common/foot.jsp"/>
</div>
</body>
</html>