<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function checkForm(){
		var articalTypeName=document.getElementById("articalTypeName").value;
		if(articalTypeName==null||articalTypeName==""){
			document.getElementById("error").innerHTML="ArticalType cannot be NULL!";
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<div class="data_list backMain">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div class="data_content">
		<form action="articalType?action=save" method="post" onsubmit="return checkForm()">
			<table cellpadding="5">
				<tr>
					<td>
						<label>ArticalTypeName</label>
					</td>
					<td>
						<input type="text" id="typeName" name="typeName" value="${articalType.typeName }"/>
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" id="artialTypeId" name="articalTypeId" value="${articalType.articalTypeId }"/>
					</td>
					<td>
						<input type="submit" class="btn btn-primary" value="저장"/>&nbsp;&nbsp;
						<input type="button" class="btn btn-primary" value="뒤로" onclick="javascript:history.back()"/>&nbsp;&nbsp;<font id="error" color="red">${error }</font>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
</div>
</body>
</html>