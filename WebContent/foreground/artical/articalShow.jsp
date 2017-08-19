<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="data_list">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div>
		<div class="artical_title"><h3>${artical.title }</h3></div>
		<div class="artical_info">
			작성일：[<fmt:formatDate value="${artical.publishDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>]&nbsp;&nbsp;글쓴이：${artical.author }
			&nbsp;&nbsp;분류：[${artical.typeName }]&nbsp;&nbsp;조회：${artical.click }
		</div>
		<div class="artical_content">
			${artical.content }
		</div>
	</div>
	<div class="upDownPage">
		${pageCode }
	</div>
</div>