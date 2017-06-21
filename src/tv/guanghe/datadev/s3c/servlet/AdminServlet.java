package tv.guanghe.datadev.s3c.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import tv.guanghe.datadev.s3c.bean.DealResult;
import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.commons.Page;
import tv.guanghe.datadev.s3c.service.DocService;
import tv.guanghe.datadev.s3c.service.impl.DocServiceImpl;
import tv.guanghe.datadev.s3c.util.DocSearchUtil;

public class AdminServlet extends HttpServlet {
	private DocService docService = new DocServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置request的编码格式
		request.setCharacterEncoding("utf-8");
		// 设置response的编码格式
		response.setContentType("text/html;charset=utf-8");

		String op = request.getParameter("op");
		if (op == null || op.trim().length() == 0) {
			op = "getDocs";
			
		}
		try {
			if ("getDocs".equals(op)) {
				// 获得文档列表
				getDocs(request, response);
			}else if ("addDoc".equals(op)) {
				// 添加文档
				addDoc(request, response);
			}else if ("editDocUI".equals(op)) {
				// 转发到编辑文档界面
				editDocUI(request, response);
			}else if ("editDoc".equals(op)) {
				// 编辑文档
				editDoc(request, response);
			}else if ("deleteDoc".equals(op)) {
				// 删除文档
				deleteDoc(request, response);
			}else if ("rebuildIndex".equals(op)) {
				// 重建索引
				rebuildIndex(request, response);
			}else{
				response.sendRedirect(request.getContextPath()+"/admin");
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("服务器忙");
		}

	}
	
	
	
	private void rebuildIndex(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		DocSearchUtil.rebuildIndex();
		redirectSuccess(request, response, "操作成功", "搜索索引将在稍后更新完毕...", "/admin/index.jsp");
		
	}
	
	private void deleteDoc(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String docId = request.getParameter("id");
		
		if(docId == null || docId.trim().equals("")){
			redirectFail(request, response, "删除文档失败", "未指定文档id", "/admin/index.jsp");
			return;
		}
		
		DealResult dealResult = docService.deleteDoc(docId);
		if(dealResult.isSuccess()){
			redirectSuccess(request, response, "删除文档成功", "搜索索引将在稍后更新完毕...", "/admin/index.jsp");
		}else{
			redirectFail(request, response, "删除文档失败", dealResult.getDesc(), "/admin/index.jsp");
		}
		

	}
	
	private void editDoc(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String docId = request.getParameter("id");
		String docTitle = request.getParameter("title");
		String docUrl = request.getParameter("url");
		String docTag = request.getParameter("tag");
		String docContent = request.getParameter("content");
		
		if(docId == null || docId.trim().equals("")){
			redirectFail(request, response, "编辑文档失败", "未指定文档id", "/admin/index.jsp");
			return;
		}else if(docTitle == null || docTitle.trim().equals("")){
			redirectFail(request, response, "编辑文档失败", "请输入文档标题", "/admin/index.jsp");
			return;
		}else if(docUrl == null || docUrl.trim().equals("")){
			redirectFail(request, response, "编辑文档失败", "请输入文档url", "/admin/index.jsp");
			return;
		}else if(docTag == null || docTag.trim().equals("")){
			redirectFail(request, response, "编辑文档失败", "请输入文档tag", "/admin/index.jsp");
			return;
		}else if(docContent == null || docContent.trim().equals("")){
			redirectFail(request, response, "编辑文档失败", "请输入文档内容", "/admin/index.jsp");
			return;
		}
		Doc doc = new Doc();
		doc.setTitle(docTitle);
		doc.setUrl(docUrl);
		doc.setTags(docTag);
		doc.setContent(docContent);
		DealResult dealResult = docService.editDoc(docId,doc);
		if(dealResult.isSuccess()){
			redirectSuccess(request, response, "编辑文档成功", "搜索索引将在稍后更新完毕...", "/admin/index.jsp");
		}else{
			redirectFail(request, response, "编辑文档失败", dealResult.getDesc(), "/admin/index.jsp");
		}
		

	}
	
	private void editDocUI(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter("id");
		Doc doc = docService.getDocById(id);
		request.setAttribute("doc",doc);
		request.getRequestDispatcher("/admin/edit_doc.jsp").forward(request, response);

	}
	
	private void getDocs(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String pageNum = request.getParameter("pageNum");
		Page page = docService.getDocsByPage(pageNum);
		page.setUrlPattern("/admin/AdminServlet?op=getDocs&pageNum=");
		request.setAttribute("page",page);
		request.setAttribute("servletFlag", true);
		request.getRequestDispatcher("/admin/index.jsp").forward(request, response);

	}

	private void addDoc(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String docTitle = request.getParameter("title");
		String docUrl = request.getParameter("url");
		String docTag = request.getParameter("tag");
		String docContent = request.getParameter("content");
		if(docTitle == null || docTitle.trim().equals("")){
			redirectFail(request, response, "添加文档失败", "请输入文档标题", "/admin/add_doc.jsp");
			return;
		}else if(docUrl == null || docUrl.trim().equals("")){
			redirectFail(request, response, "添加文档失败", "请输入文档url", "/admin/add_doc.jsp");
			return;
		}else if(docTag == null || docTag.trim().equals("")){
			redirectFail(request, response, "添加文档失败", "请输入文档tag", "/admin/add_doc.jsp");
			return;
		}else if(docContent == null || docContent.trim().equals("")){
			redirectFail(request, response, "添加文档失败", "请输入文档内容", "/admin/add_doc.jsp");
			return;
		}
		Doc doc = new Doc();
		doc.setTitle(docTitle);
		doc.setUrl(docUrl);
		doc.setTags(docTag);
		doc.setContent(docContent);
		DealResult dealResult = docService.addDoc(doc);
		if(dealResult.isSuccess()){
			redirectSuccess(request, response, "添加文档成功", "搜索索引将在稍后更新完毕...", "/admin/index.jsp");
		}else{
			redirectFail(request, response, "添加文档失败", dealResult.getDesc(), "/admin/add_doc.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private void redirectSuccess(HttpServletRequest request, HttpServletResponse response, String title,String content, String path)
			throws ServletException, IOException {
		request.setAttribute("notification_type", "panel-primary");
		redirectSetContent(request, response, title, content, path);
	}
	
	private void redirectFail(HttpServletRequest request, HttpServletResponse response, String title,String content, String path)
			throws ServletException, IOException {
		request.setAttribute("notification_type", "panel-danger");
		redirectSetContent(request, response, title, content, path);
	}
	
	private void redirectSetContent(HttpServletRequest request, HttpServletResponse response, String title,String content, String path)
			throws ServletException, IOException {
		request.setAttribute("notification_title", title);
		request.setAttribute("notification_content", content);
		request.setAttribute("jump_path", path);
		request.getRequestDispatcher("/admin/notification.jsp").forward(request, response);
	}
}
