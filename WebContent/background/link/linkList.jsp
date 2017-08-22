<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function linkDelete(linkId){
		if(confirm("이 핫링크를 삭제하시겠어요?")){
			$.post("link?action=delete",{linkId:linkId},
				function(result){
					var result=eval('('+result+')');
					if(result.success){
						alert("삭제완성!");
						window.location.href="${pageContext.request.contextPath}/link?action=backList";
					}else{
						alert(result.errorMsg);
					}
				}
			);
		}
	}
</script>
</head>
<body>
<div class="data_list backMain">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div class="data_content">
		<table class="table table-hover table-bordered">
			<tr>
				<th>LinkNum</th>
				<th>LinkName</th>
				<th>LinkUrl</th>
				<th>LinkEmail</th>
				<th>OrderNum</th>
				<th>조작</th>
			</tr>
			<c:forEach var="linkBack" items="${linkBackList }" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${linkBack.linkName }</td>
					<td>${linkBack.linkUrl }</td>
					<td>${linkBack.linkEmail }</td>
					<td>${linkBack.orderNum }</td>
					<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='link?action=preSave&linkId=${linkBack.linkId}'">수정</button>&nbsp;&nbsp;<button class="btn btn-mini btn-danger" type="button" onclick="linkDelete(${linkBack.linkId})">삭제</button></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
</body>
</html>