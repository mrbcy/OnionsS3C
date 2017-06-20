<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<title>Onions S3C</title>
		<link style="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/search_result_main.css">
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/icon/favicon.png">
		<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
		<meta charset="UTF-8">
	</head>
	<body>
		<div id="container">
			<div id="header">
				<div id="left-header">
					<div id="logo-container">
						<img id="logo" src="images/onionDataLogo_small.png">
					</div>
					<div id="searchbar-container">
						<form action="${pageContext.request.contextPath }/search" method="post">
							<input type="text" id="searchbar" value="${keyword }" name="keyword" >
							<input type="submit" value="Search" id="searchbutton">
						</form>
					</div>
				</div>
				
				<div id="right-header">
					
				</div>
				
			</div>
			
			
			<div id="body">
				<div>
					<p id="results-count">About ${page.totalRecordsNum } results (index updated at ${lastIndexModifiedTiem })</p>
				</div>
				<c:if test="${empty page.records }">
					<div style="height:500px; width: 480px; font-size:20px; display: table-cell;">
						<span>没有找到包含 <b>${keyword }</b> 的结果</span>
					</div>
				</c:if>
				<c:if test="${!empty page.records }">
					<c:forEach items="${page.records}" var="doc" varStatus="vs">
						<div>
							<a href="${doc.url }" target="_blank"><h3>${doc.title }</h3></a>
							<span class="result-url">${doc.url }</span>
							<a href="${pageContext.request.contextPath}/cache?id=${doc.id }&highlight=${keyword}" target="_blank">
							<span class="link-arrow" style="margin-right:8px"></span>
							文档快照</a>
							<p class="linkdesc">${doc.content }</p>
						</div>
					</c:forEach>
					
					
					<div id="searchnav">
						<%@include file="/commons/paging.jsp" %>
					</div>
				</c:if>
				
			</div>	
			<script type="text/javascript">
			$(function() {
			    //获取焦点后光标在字符串后 
				var t=$("#searchbar").val(); 
				$("#searchbar").val("").focus().val(t); 
			});
				
			</script>
			
			
		</div>
	

</body>
</html>
