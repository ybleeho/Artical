<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function newsTypeDelete(newsTypeId){
		if(confirm("이 문장종류를 삭제하시겠습니까?")){
			$.post("articalType?action=delete",{articalTypeId:articalTypeId},
				function(result){
					var result=eval('('+result+')');
					if(result.success){
						alert("삭제성공!");
						window.location.href="${pageContext.request.contextPath}/articalType?action=backList";
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
				<th>TypeNum</th>
				<th>TypeName</th>
				<th>조작</th>
			</tr>
			<c:forEach var="articalTypeBack" items="${articalTypeBackList }" varStatus="status">
				<tr>
					<td>${status.index+1 }</td>
					<td>${articalTypeBack.typeName }</td>
					<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='articalType?action=preSave&articalTypeId=${articalTypeBack.articalTypeId}'">수정</button>&nbsp;&nbsp;<button class="btn btn-mini btn-danger" type="button" onclick="articalTypeDelete(${articalTypeBack.articalTypeId})">삭제</button></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
</body>
</html>