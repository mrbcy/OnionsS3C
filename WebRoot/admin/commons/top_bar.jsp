<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<header class="top-hd">
			<div class="hd-lt">
				<a class="icon-reorder"></a>
			</div>
			<div class="hd-rt">
				<ul>
					<li>
						<a href="${pageContext.request.contextPath}"><i class="icon-home"></i>前台访问</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/admin/AdminServlet?op=rebuildIndex"><i class="icon-random"></i>重建索引</a>
					</li>
					<li>
						<a><i class="icon-user"></i>管理员:<em>${sysAdminName}</em></a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/PublicServlet?op=logout" id="JsSignOut"><i class="icon-signout"></i>安全退出</a>
					</li>
				</ul>
			</div>
		</header>
