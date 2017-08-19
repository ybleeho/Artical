<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="data_list">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div class="datas artical_type_list">
		<ul>
			<c:forEach var="newestArtical" items="${newestArticalListWithType }">
				<li><span>[<fmt:formatDate value="${newestArtical.publishDate }" type="date" pattern="yyyy-MM-dd"/>]</span>&nbsp;&nbsp;<a href="artical?action=show&articalId=${newestArtical.articalId }" title="${newestArtical.title }">${fn:substring(newestArtical.title,0,42) }</a></li>
			</c:forEach>
		</ul>
	</div>
	<div>
		${pageCode }
	</div>
</div>