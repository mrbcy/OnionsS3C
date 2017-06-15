<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
第${page.curPageNum}页/共${page.totalPage}页 &nbsp;&nbsp;
<a href="${pageContext.request.contextPath }${page.urlPattern}">首页</a>&nbsp;
<a href="${pageContext.request.contextPath }${page.urlPattern}${page.prevPageNum}">上一页</a>&nbsp;
<a href="${pageContext.request.contextPath }${page.urlPattern}${page.nextPageNum}">下一页</a>&nbsp;
<a href="${pageContext.request.contextPath }${page.urlPattern}${page.totalPage}">尾页</a>&nbsp;
<input type="text" size="2"  id="txtPageNum"/><input type="button" value="跳转" onclick="jump();" id="jumppagebutton">
<script type="text/javascript">
function jump(){
	var pageNum = document.getElementById("txtPageNum").value;
	// 验证
	if(!/^[1-9][0-9]*$/.test(pageNum)){
		alert('请输入正确的页数');
		return;
	}
	if(pageNum < 1 || pageNum > ${page.totalPage}){
		alert('页码超出范围');
		return;
	}
	window.location.href = '${pageContext.request.contextPath }${page.urlPattern}'+pageNum;
}
</script>
