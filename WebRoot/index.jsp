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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cssreset.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home_main.css">
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/icon/favicon.png">
		<meta charset="UTF-8">
	</head>
	<body>
		<div id="container">
			<div id="header">				
				<nav>
					<ul>
					<c:if test="${empty sessionScope.sysAdminName}">
						<li><a href="${pageContext.request.contextPath}/login.jsp">登录</a></li>
					</c:if>
					<c:if test="${!empty sessionScope.sysAdminName}">
						<li><a href="${pageContext.request.contextPath}/admin">${sessionScope.sysAdminName }</a></li>
					</c:if>
					
						<li></li>
						<li></li>
					</ul>
				</nav>
			</div>
			<div id="body">
				<div>
					<h1 id="logo">Google</h1>
					
				</div>
				<form action="${pageContext.request.contextPath }/search" method="post">
					<input type="text" name="keyword" autofocus="autofocus">
					<input type="submit" value="Search Docs">
				</form>
			</div>
			
		</div>
	

	</body>
</html>

