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

<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/icon/favicon.png">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin_style.css" />
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/plug-ins/customScrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/js/plug-ins/layerUi/layer.js"></script>
<script src="${pageContext.request.contextPath}/js/public.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor/samples/js/sample.js"></script>
</head>
<body>
<div class="main-wrap">
	<%@include file="/admin/commons/slide_bar.jsp" %>
	<div class="content-wrap">
		<%@include file="/admin/commons/top_bar.jsp" %>
		<main class="main-cont content mCustomScrollbar">

			<!--开始::内容-->
            <div class="page-wrap">
                <form action="${pageContext.request.contextPath}/admin/AdminServlet" method="post">
                	<input type="hidden" name="op" value="addDoc"></input>
                    <div class="form-group-col-2">
                        <div class="form-label">文档标题：</div>
                        <div class="form-cont">
                            <input type="text" name="title" placeholder="文档标题..." class="form-control form-boxed">
                        </div>
                    </div>
                    <div class="form-group-col-2">
                        <div class="form-label">url：</div>
                        <div class="form-cont">
                            <input type="text" name="url" placeholder="留空则发布到系统内" class="form-control form-boxed" style="width:400px;">
                        </div>
                    </div>
                    <div class="form-group-col-2">
                        <div class="form-label">tags：</div>
                        <div class="form-cont">
                            <input type="text" name="tag" placeholder="标签..." class="form-control form-boxed" style="width:400px;">
                        </div>
                    </div>
                    <!--开始::结束-->
                    <textarea id="editor" name="content"></textarea>
                    <!--<div id="editor">-->
                    <!--&lt;!&ndash;<h1>Hello world!</h1>&ndash;&gt;-->
                    <!--&lt;!&ndash;<p>I'm an instance of <a href="http://ckeditor.com">CKEditor</a>.</p>&ndash;&gt;-->
                    <!--</div>-->
                    <div class="form-group-col-2 rt" style="margin-top : 15px">
                        <div class="form-label"></div>
                        <div class="form-cont">
                            <input type="submit" class="btn btn-primary" value="发表" />
                            <a href="index.jsp" class="btn btn-secondary">返回</a>
                        </div>
                    </div>
                </form>

            </div>
            <!--开始::结束-->
		</main>
		<%@include file="/admin/commons/footer.jsp" %>
	</div>
</div>
<script>
    init_editor();
</script>
</body>
</html>

