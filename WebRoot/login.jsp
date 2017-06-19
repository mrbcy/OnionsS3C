<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>Onions S3C - 后台管理</title>
<meta name="keywords"  content="设置关键词..." />
<meta name="description" content="设置描述..." />
<meta name="author" content="DeathGhost" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name='apple-touch-fullscreen' content='yes'>
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="address=no">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/icon/favicon.png">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin_style.css" />
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/public.js"></script>
<script src="${pageContext.request.contextPath}/js/plug-ins/customScrollbar.min.js"></script>
</head>
<body class="login-page">
	<section class="login-contain">
		<header>
			<h1>后台管理</h1>
			<p>Onions S3C</p>
		</header>
		<form action="${pageContext.request.contextPath}/PublicServlet" method="post">
			<input type="hidden" name="op" value="login"/>
			<div class="form-content">
				<ul>
					<li>
						<div class="form-group">
							<label class="control-label">管理员账号：</label>
							<input type="text" placeholder="管理员账号..." class="form-control form-underlined" id="adminName" name="adminName"/>
						</div>
					</li>
					<li>
						<div class="form-group">
							<label class="control-label">管理员密码：</label>
							<input type="password" placeholder="管理员密码..." class="form-control form-underlined" id="adminPwd" name="adminPwd"/>
						</div>
					</li>
					
					<li>
						<input type="submit" class="btn btn-lg btn-block btn-primary" id="entry" value="立即登录"/>
					</li>
					<li>
						<p class="btm-info">©Copyright 2017 <a href="#" title="DataDev">DataDev</a></p>
					</li>
				</ul>
			</div>
		</form>
		
	</section>

</body>
</html>
