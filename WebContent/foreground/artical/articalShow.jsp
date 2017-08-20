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

<div class="data_list comment_list"> 
	<div class="dataHeader">댓글</div>
	<div class="commentDatas">
		<c:forEach var="comment" items="${commentList }">
			<div class="comment">
				<font>${comment.userIP }：</font>${comment.content }&nbsp;&nbsp;&nbsp;[&nbsp;<fmt:formatDate value="${comment.commentDate }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;]
			</div>
		</c:forEach>
	</div>
</div>

<div class="publish_list">
	<form action="comment?action=save" method="post">
		<div>
			<input type="hidden" value="${artical.articalId }" id="articalId" name="articalId"/>
			<textarea style="width: 98%" rows="3" id="content" name="content"></textarea>
		</div>
		<div class="publishButton">
			<button class="btn btn-primary" type="submit">댓글달기</button>
		</div>
	</form>
</div>