package tv.guanghe.datadev.s3c.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import tv.guanghe.datadev.s3c.bean.Doc;
import tv.guanghe.datadev.s3c.service.DocService;
import tv.guanghe.datadev.s3c.service.impl.DocServiceImpl;
import tv.guanghe.datadev.s3c.util.WordUtil;

public class DocCacheServlet extends HttpServlet{
	private DocService docService = new DocServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String keyword = request.getParameter("highlight");
		
		Doc doc = docService.getDocById(id);
		List<String> segments = new ArrayList<String>();
		
		if(keyword != null){
			segments = WordUtil.getSegments(keyword);
			keyword = WordUtil.highLight(keyword, segments);
		}
		
		if(doc != null){
			doc.setTitle(WordUtil.highLight(doc.getTitle(), segments));
			String content = doc.getContent().replace("<code>", "").replace("</code>", "");
			doc.setContent(WordUtil.highLight(content, segments));
		}
		request.setAttribute("keyword",keyword);
		request.setAttribute("doc", doc);
		request.getRequestDispatcher("/cache.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
