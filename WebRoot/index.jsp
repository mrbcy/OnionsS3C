<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<meta charset="UTF-8">
	</head>
	<body>
		<div id="container">
			<div id="header">				
				<nav>
					<ul>
						<li><a href="#">登录</a></li>
						<li></li>
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
					<input type="text" name="keyword">
					<input type="submit" value="Search Docs">
				</form>
			</div>
			
		</div>
	

	</body>
</html>

