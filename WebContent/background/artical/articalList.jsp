<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
</head>
<script type="text/javascript">
function articalDelete(articalId){
	if(confirm("이문장을 삭제하시겠나요？")){
		$.post("artical?action=delete",{articalIds:articalId},
			function(result){
				var flag=eval('('+result+')');
				if(result){
					alert("삭제성공!");
					window.location.href="${pageContext.request.contextPath}/artical?action=backList";
				}else{
					alert("삭제실패");
				}
			}
		);
	}
}

function articalsDelete(){
	var chk_value=[];
	$('input[name="articalIds"]:checked').each(function(){
		chk_value.push($(this).val());
	});
	if(chk_value.length==0){
		alert("삭제 할 문장을 선택해주십시오");
		return;
	}
	var articalIds=chk_value.join(",");
	if(confirm("이 댓글들을 삭제하시겠습니까？")){
		$.post("artical?action=delete",{articalIds:articalIds},
			function(result){
				var result=eval('('+result+')');
				if(result.success){
					alert(+result.delNums+"문장을 삭제성공");
					window.location.href="${pageContext.request.contextPath}/artical?action=backList";
				}else{
					alert(result.errorMsg);
				}
			}
		);
	}
}

$(document).ready(function(){
	$("#checkedAll").click(function(){
		if($(this).prop("checked")==true){
			$("input[name='articalIds']").prop("checked",true);
		}else{
			$("input[name='articalIds']").prop("checked",false);
		}
	});
	
});
</script>
<body>
<div class="data_list backMain">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div class="search_content" style="vertical-align: middle;">
		<div style="float: left;padding-top: 10px;">
			<button class="btn btn-mini btn-danger" type="button" onclick="articalsDelete()">대량삭제</button>&nbsp&nbsp
		</div>
		<form action="${pageContext.request.contextPath}/artical?action=backList" method="post">
			ArticalTitle：<input type="text" id="s_title" name="s_title" style="width:180px" value="${s_title }"/>&nbsp;&nbsp;
			PubDate：<input type="text" id="s_bPublishDate" name="s_bPublishDate" class="Wdate" onclick="WdatePicker()" style="width: 100px;" value="${s_bPublishDate }"/>
			&nbsp;부터&nbsp;<input type="text" id="s_aPublishDate" name="s_aPublishDate" class="Wdate" onclick="WdatePicker()" style="width: 100px;" value="${s_aPublishDate }"/>
			&nbsp;&nbsp;<button class="btn btn-mini btn-primary" type="submit" style="margin-top: -8px;">검색</button>
		</form>
	</div>
	
	<div class="data_content">
		<table class="table table-hover table-bordered">
			<tr>
				<th><input type="checkbox" id="checkedAll"/></th>
				<th>Num</th>
				<th>ArticalTilte</th>
				<th>ArticalType</th>
				<th>PubDate</th>
				<th>조작</th>
			</tr>
			<c:forEach var="articalBack" items="${articalBackList }" varStatus="status">
				<tr>
					<td><input type="checkbox" name="articalIds" value="${articalBack.articalId}"/></td>
					<td>${status.index+1 }</td>
					<td>${articalBack.title }</td>
					<td>${articalBack.typeName }</td>
					<td><fmt:formatDate value="${articalBack.publishDate }" type="date" pattern="yyyy-MM-dd"/></td>
					<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location.href='artical?action=preSave&articalId=${articalBack.articalId}'">수정</button>&nbsp;<button class="btn btn-mini btn-danger" type="button" onclick="articalDelete(${articalBack.articalId})">삭제</button></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div class="pagination pagination-centered">
  		<ul>
  			${pageCode }
  		</ul>
  	</div>
</div>
</body>
</html>