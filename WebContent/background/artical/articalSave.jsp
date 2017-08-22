<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function checkChange(){
		if(document.getElementById("isImage").checked){
			$("#hdtp").show();
		}else{
			$("#hdtp").hide();
		}
	}
	
	function checkForm(){
		var title=document.getElementById("title").value;
		var author=document.getElementById("author").value;
		var typeId=document.getElementById("typeId").value;
		var content=CKEDITOR.instances.content.getData();
		if(title==null||title==""){
			document.getElementById("error").innerHTML="ArticalTile cannot be NULL！";
			return false;
		}
		if(author==null||author==""){
			document.getElementById("error").innerHTML="Author cannot be NULL！";
			return false;
		}
		if(typeId==null||typeId==""){
			document.getElementById("error").innerHTML="Please choose the articalType！";
			return false;
		}
		if(content==null||content==""){
			document.getElementById("error").innerHTML="ArtocalContent can not be NULL！";
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
		<form action="artical?action=save" method="post" enctype="multipart/form-data" onsubmit="return checkForm()">
			<table cellpadding="5" width="100%">
				<tr>
					<td width="80px">
						<label>ArticalTitle：</label>
					</td>
					<td>
						<input type="text" id="title" name="title" class="input-xxlarge"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>ArticalAuthor：</label>
					</td>
					<td>
						<input type="text" id="author" name="author" />
					</td>
				</tr>
				<tr>
					<td>
						<label>ArticalType：</label>
					</td>
					<td>
						<select id="typeId" name="typeId">
							<option value="">Choose the articalType</option>
							<c:forEach var="articalType" items="${articalTypeList }">
								<option value="${articalType.articalTypeId }">${articalType.typeName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>ArticalAttribute：</label>
					</td>
					<td>
						<label class="checkbox inline">
						  <input type="checkbox" id="isHead" name="isHead" value="1">Head
						</label>
						<label class="checkbox inline">
						  <input type="checkbox" id="isImage" name="isImage" onclick="checkChange()" value="1">Slide
						</label>
						<label class="checkbox inline">
						  <input type="checkbox" id="isHot" name="isHot" value="1">Hot
						</label>
					</td>
				</tr>
				<tr id="hdtp" style="display: none">
					<td>
						<label>SlideImage：</label>
					</td>
					<td>
						<input type="file" id="picFile" name="picFile" />
					</td>
				</tr>
				<tr>
					<td valign="top">
						<label>ArticalContent：</label>
					</td>
					<td>
						<textarea class="ckeditor" id="content" name="content"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
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