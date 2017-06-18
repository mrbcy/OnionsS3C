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
		<title>Onions S3C - 快照</title>
		<link style="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/cache.css">

		<meta charset="UTF-8">
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/icon/favicon.png">
	</head>
	<body>
		<div id="container">
			<div id="header">
				<div id="left-header">
					<div id="logo-container">
						<img id="logo" src="images/onionDataLogo_small.png">
					</div>
					
				</div>
				
				<div id="right-header">
					
				</div>
				
			</div>
			
			
			<div id="body" style="font-size:16px; ">
				<c:if test="${!empty keyword}">
					<p style="font-size:24px; ">您检索的关键字为：${keyword }</p>
				</c:if>
				
				<br><br><br>
				<c:if test="${empty doc}">
					<h1>文档快照不存在...</h1>
					<br><br><br>
					<script>
					    window.setTimeout("window.location='${pageContext.request.contextPath}'",2000); 
					</script>
				</c:if>
				<c:if test="${!empty doc}">
					<h1>${doc.title }</h1>
					<hr/>
					${doc.content }
				</c:if>
							
				
			</div>	
			
			
			
		</div>
	

</body>
</html>
