<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link href="${pageContext.request.contextPath}/style/artical.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
</head>
<body>
<div class="container">
<jsp:include page="/foreground/common/head.jsp"/>

<div class="row-fluid">
	<div class="span9">
		<div>
			<DIV style="width: 330px; height: 228px;" class="tuhuo">
				<A href="" target="_blank"><IMG style="width: 330px; height: 208px;" id="fou_img" src=""></A>
					<c:forEach var="imageArtical" items="${imageArticalList}">
						<A href="artical?action=show&articalId=${imageArtical.articalId }"> 
							<IMG style="display: none;" class="tu_img" src="${imageArtical.imageName}" width="330" height="208" />
						</A>
					</c:forEach>
					<c:forEach var="imageArtical" items="${imageArticalList}">
						<P style="height: 20px;" class="tc"><A href="artical?action=show&articalId=${imageArtical.articalId }" target="_blank" title="${imageArtical.title }">${fn:substring(imageArtical.title,0,18) }</A></P>
					</c:forEach>

					<UL>
  						<LI class="fouce">1</LI>
  						<LI>2</LI>
  						<LI>3</LI>
  						<LI>4</LI>
  						<LI>5</LI>
  					</UL>
  			</DIV>
			
		</div>
		<div class="articalHeader_list">
			<div class="articalHeader">
				<h3><a href="artical?action=show&articalId=${headArtical.articalId }" title="${headArtical.title }">${fn:substring(headArtical.title,0,10) }</a></h3>
				<p>${fn:substring(headArtical.content,0,40) }...<a href="artical?action=show&articalId=${headArtical.articalId }" >[전체보기]</a></p>
			</div>
			<div class="currentUpdate">
				<div class="currentUpdateHeader">New</div>
				<div class="currentUpdateDatas">
				<table width="100%">
					<c:forEach var="newestArtical" items="${newestArticalList }" varStatus="status">
						<c:if test="${status.index%2==0 }">
							<tr>
						</c:if>
						<td width="50%">
							<a href="artical?action=show&articalId=${newestArtical.articalId }" title="${newestArtical.title }">${fn:substring(newestArtical.title,0,12) }</a>
						</td>
						<c:if test="${status.index%2==1 }">
							</tr>
						</c:if>
					</c:forEach>
				</table>
				</div>
			</div>
		</div>
	</div>
	<div class="span3">
		<div class="data_list hotspot_artical_list">
			<div class="dataHeader">Hot</div>
			<div class="datas">
				<ul>
					<c:forEach var="hotSpotArtical" items="${hotSpotArticalList }">
						<li><a href="artical?action=show&articalId=${hotSpotArtical.articalId }" title="${hotSpotArtical.title }">${fn:substring(hotSpotArtical.title,0,15) }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>

<c:forEach var="allIndexArtical" items="${allIndexArticalList }" varStatus="allStatus">
	<c:if test="${allStatus.index%3==0 }">
		<div class="row-fluid">
	</c:if>
	<c:forEach var="indexArtical" items="${allIndexArtical }" varStatus="oneStatus">
		<c:if test="${oneStatus.first }">
			<div class="span4">
			<div class="data_list artical_list" >
				<div class="dataHeader">${articalTypeList.get(allStatus.index).typeName }<span class="more"><a href="artical?action=list&typeId=${articalTypeList.get(allStatus.index).articalTypeId }">more+</a></span></div>
				<div class="datas">
					<ul>
		</c:if>
		<li><fmt:formatDate value="${indexArtical.publishDate }" type="date" pattern="MM-dd"/>&nbsp;<a href="artical?action=show&articalId=${indexArtical.articalId }" title="${indexArtical.title }">${fn:substring(indexArtical.title,0,18) }</a></li>
		<c:if test="${oneStatus.last }">
					</ul>
						</div>
					</div>
				</div>
		</c:if>
	</c:forEach>
	<c:if test="${allStatus.index%3==2 || allStatus.last }">
		</div>
	</c:if>
</c:forEach>
</div>
<jsp:include page="/foreground/common/link.jsp"/>
<jsp:include page="/foreground/common/foot.jsp"/>

</body>
<script type="text/javascript">
	var auto;
	var index=0;
	$('.tuhuo ul li').hover(function(){
		clearTimeout(auto);
		index=$(this).index();
		move(index);
		},function(){
			auto=setTimeout('autogo('+index+')',3000);
	});
	
	function autogo(){
		if(index<5){
			move(index);
			index++;
		}
		else{
			index=0;	
			move(index);
			index++;
		}
	}
	function move(l){
		var src=$('.tu_img').eq(index).attr('src');
		$("#fou_img").css({  "opacity": "0"  });
		$('#fou_img').attr('src',src);
		$('#fou_img').stop(true).animate({ opacity: '1'},1000);
		$('.tuhuo ul li').removeClass('fouce');
		$('.tuhuo ul li').eq(index).addClass('fouce');
		$('.tuhuo p').hide();
		$('.tuhuo p').eq(index).show();
		var ao=$('.tuhuo p').eq(index).children('a').attr('href');
		$('#fou_img').parent('a').attr("href",ao);
	}
	autogo();
	setInterval('autogo()',3000);
</script>
</html>