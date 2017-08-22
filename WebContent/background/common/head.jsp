<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	function setDateTime(){
			var date=new Date();
			var day=date.getDay();
			var week;
			switch(day){
			case 0:week="일요일";break;
			case 1:week="월요일";break;
			case 2:week="화요일";break;
			case 3:week="수요일";break;
			case 4:week="목욕일";break;
			case 5:week="금요일";break;
			case 6:week="토요일";break;
			}
			var today=date.getFullYear()+"년"+(date.getMonth()+1)+"월"+date.getDate()+"일  "+week+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
			document.getElementById("today").innerHTML=today;
		}
		
		window.setInterval("setDateTime()", 1000);
		
		function logout(){
			if(confirm('로그아웃 하시겠나요？')){
				window.location.href='user?action=logout';
			}
		}
	</script>
<div class="row-fluid">
	<div class="span12">
		<div>
			<div class="headLeft">
				<img src="${pageContext.request.contextPath}/images/logo_back.png"/>
			</div>
			<div class="headRight">
				매니저：<font color="red">${currentUser.userName }</font>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:logout()">[&nbsp;로그아웃&nbsp;]</a>&nbsp;&nbsp;&nbsp;&nbsp;<font id="today" class="currentDateTime"></font>
			</div>
		</div>
	</div>
</div>
