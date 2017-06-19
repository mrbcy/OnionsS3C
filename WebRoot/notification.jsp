<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>Onions S3C</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/icon/favicon.png">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin_style.css" />
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/public.js"></script>
</head>
<body>
<div class="main-wrap">
	<div class="content-wrap">
		<main class="main-cont content mCustomScrollbar">

			<!--开始::内容-->
            <div class="page-wrap">
                <div class="panel panel-default">
					<div class="panel-bd">
						<div class="panel ${notification_type }">
							<div class="panel-hd">${notification_title }</div>
							<div class="panel-bd">
								<br><br>${notification_content }<br><br><br>
							</div>
						</div>
					</div>
				</div>

            </div>
            <!--开始::结束-->
		</main>
	</div>
</div>
<script>
    window.setTimeout("window.location='${pageContext.request.contextPath}${jump_path }'",2000); 
</script>
</body>
</html>

