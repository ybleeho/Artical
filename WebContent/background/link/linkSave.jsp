<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function checkForm(){
		var linkName=document.getElementById("linkName").value;
		var linkUrl=document.getElementById("linkUrl").value;
		var linkEmail=document.getElementById("linkEmail").value;
		var orderNum=document.getElementById("orderNum").value;
		if(linkName==null||linkName==""){
			document.getElementById("error").innerHTML="LinkName cannot be NULL!";
			return false;
		}
		if(linkUrl==null||linkUrl==""){
			document.getElementById("error").innerHTML="LinkUrl cannot be NULL!";
			return false;
		}
		if(linkEmail==null||linkEmail==""){
			document.getElementById("error").innerHTML="LinkEmail cannot be NULL!";
			return false;
		}
		if(orderNum==null||orderNum==""){
			document.getElementById("error").innerHTML="OrderNum cannnot be NULL!";
			return false;
		}else{
			 var type="^[0-9]*[1-9][0-9]*$"; 
		     var re=new RegExp(type); 
	         if(orderNum.match(re)==null){ 
	           alert("OrderNum must be a positive integer!"); 
	           return false;
	        }
			return true;
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
		<form action="link?action=save" method="post" onsubmit="return checkForm()">
			<table cellpadding="5">
				<tr>
					<td>
						<label>LinkName：</label>
					</td>
					<td>
						<input type="text" id="linkName" name="linkName" value="${link.linkName }"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>LinkAddress：</label>
					</td>
					<td>
						<input type="text" id="linkUrl" name="linkUrl" value="${link.linkUrl }"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>LinkEmail：</label>
					</td>
					<td>
						<input type="text" id="linkEmail" name="linkEmail" value="${link.linkEmail }"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>OrderNum：</label>
					</td>
					<td>
						<input type="text" id="orderNum" name="orderNum" value="${link.orderNum }"/>
					</td>
				</tr>
				<tr>
					<td>
						<input type="hidden" id="linkId" name="linkId" value="${link.linkId }"/>
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