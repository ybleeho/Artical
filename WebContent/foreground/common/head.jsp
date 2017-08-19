<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="row-fluid">
	<div class="span12">
		<img src="${pageContext.request.contextPath}/images/logo.png">
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="navbar">
		  <div class="navbar-inner">
		    <a class="brand" href="goIndex">처음으로</a>
		    <ul class="nav">
		       <c:forEach var="articalType" items="${articalTypeList}">
		       		<li><a href="artical?action=list&typeId=${articalType.articalTypeId }">${articalType.typeName }</a></li>
		       </c:forEach>
		    </ul>
		  </div>
		</div>
	</div>
</div>