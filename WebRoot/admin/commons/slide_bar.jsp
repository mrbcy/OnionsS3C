<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<div class="side-nav">
		<div class="side-logo">
			<div class="logo">
				<span class="logo-ico">
					<i class="i-l-1"></i>
					<i class="i-l-2"></i>
					<i class="i-l-3"></i>
				</span>
				<strong>Onions S3C - 后台管理</strong>
			</div>
		</div>
		
		<nav class="side-menu content mCustomScrollbar" data-mcs-theme="minimal-dark">
			<h2>
                <a href="${pageContext.request.contextPath}/admin/index.jsp" class="InitialPage"><i class="icon-laptop"></i>文档管理</a>
			</h2>
            <h2>
                <a href="${pageContext.request.contextPath}/admin/dict.jsp" class="InitialPage"><i class="icon-font"></i>自定义词典管理</a>
            </h2>
		</nav>
		<footer class="side-footer">© Powered by Datadev</footer>
	</div>