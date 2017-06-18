<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:if test="${empty servletFlag}">
	<jsp:forward page="/admin/AdminServlet"></jsp:forward> 
</c:if>

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
</head>
<body>
<div class="main-wrap">
	<%@include file="/admin/commons/slide_bar.jsp" %>
	<div class="content-wrap">
		<%@include file="/admin/commons/top_bar.jsp" %>
		<main class="main-cont content mCustomScrollbar">

			<!--开始::内容-->
            <div class="page-wrap">
                <section class="page-hd">
            <header>
                <h2 class="title">文档管理</h2>
                <p class="title-description">
                    可以在此进行文档的添加、更新、删除操作，也可以在此进行索引重建
                </p>
            </header>
            <hr>
        </section>
            <div style="margin-bottom: 15px">
                <a href="${pageContext.request.contextPath}/admin/add_doc.jsp" class="btn btn-secondary">添加文档</a>
                <button class="btn btn-warning">重建索引</button>
            </div>
            <div></div>
            <table class="table table-bordered table-striped table-hover">
                <thead>
                <tr>
                    <th>id</th>
                    <th>文档标题</th>
                    <th>url</th>
                    <th>tags</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.records}" var="doc" varStatus="vs">
					<tr class="cen">
	                    <td>${doc.id }</td>
	                    <td class="lt"><a href="#">${doc.title }</a></td>
	                    <td class="lt">${doc.url }</td>
	                    <td class="lt">${doc.tags }</td>
	                    <td>
	                        <a title="编辑" class="mr-5" href="${pageContext.request.contextPath}/admin/AdminServlet?op=editDocUI&id=${doc.id}">编辑</a>
	                        <a title="详情" class="mr-5">详情</a>
	                        <a title="删除" class="JopenMaskPanel" href="javascript:void(0);" onclick="setDeleteId(${doc.id});">删除</a>
	                    </td>
	                </tr>		
				</c:forEach>
                
                </tbody>
            </table>
            <%@include file="/commons/paging.jsp" %>
            <!--开始::结束-->
		</main>
		<%@include file="/admin/commons/footer.jsp" %>
		
		<div class="mask"></div>
		<div class="dialog">
		
			<div class="dialog-hd">
				<strong class="lt-title">操作确认</strong>
				<a class="rt-operate icon-remove JclosePanel" title="关闭"></a>
			</div>
			<div class="dialog-bd">
				<!--start::-->
				
				<p>你确定要删除这个文档吗？</p>
				
				<!--end::-->
			</div>
			<div class="dialog-ft">
				<form action="${pageContext.request.contextPath}/admin/AdminServlet" method="post">
					
					<input type="hidden" name="op" value="deleteDoc" style="display:none"/>
					<input type="hidden" name="id" value="" id="inputDoc" style="display:none"/>
					<input type="submit" class="btn btn-info" value="确认"/>
					<input type="reset" class="btn btn-secondary JclosePanel" value="关闭" onclick="resetDeleteId();"/>
				</form>
				
			</div>

		
		</div>
	</div>
</div>
<script type="text/javascript">
    $('#jumppagebutton').attr('class', 'btn btn-link');
    function setDeleteId(docId){
    	$("#inputDoc").val(docId);
    	console.log($("#inputDoc").val());
    }
    function resetDeleteId(docId){
    	$("#inputDoc").val();
    	console.log($("#inputDoc").val());
    }
</script>
</body>
</html>

