package tv.guanghe.datadev.s3c.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import tv.guanghe.datadev.s3c.commons.Page;
import tv.guanghe.datadev.s3c.service.DocService;
import tv.guanghe.datadev.s3c.service.impl.DocServiceImpl;

public class SearchServlet extends HttpServlet{
	private DocService docService = new DocServiceImpl();
			
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置request的编码格式
		request.setCharacterEncoding("utf-8");
		// 设置response的编码格式
		response.setContentType("text/html;charset=utf-8");
		
		String keyword = request.getParameter("keyword");
		System.out.println("搜索关键字：" + keyword);
		if(keyword == null || keyword.trim().equals("")){
			// 跳转到jsp
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		request.setAttribute("keyword", keyword);
		// 获得搜索结果
		Page page = docService.searchDocsByPage(keyword, request.getParameter("pageNum"));
		page.setUrlPattern("/search?keyword="+keyword+"&pageNum=");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/search_result.jsp").forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
