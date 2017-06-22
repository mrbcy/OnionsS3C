package tv.guanghe.datadev.s3c.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tv.guanghe.datadev.s3c.bean.DealResult;
import tv.guanghe.datadev.s3c.dao.SysDao;
import tv.guanghe.datadev.s3c.dao.impl.SysDaoImpl;
import tv.guanghe.datadev.s3c.global.SCProp;
import tv.guanghe.datadev.s3c.shimosync.AlertMailUtil;
import tv.guanghe.datadev.s3c.util.DocSearchUtil;

public class PublicServlet extends HttpServlet{
	private SysDao sysDao = new SysDaoImpl();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置request的编码格式
		request.setCharacterEncoding("utf-8");
		// 设置response的编码格式
		response.setContentType("text/html;charset=utf-8");

		String op = request.getParameter("op");
		if (op == null || op.trim().length() == 0) {
			op = "getLoginInfo";
			
		}
		try {
			if ("getLoginInfo".equals(op)) {
				// 获得登录身份
				getLoginInfo(request, response);
			}else if ("login".equals(op)) {
				// login
				login(request, response);
			}else if ("logout".equals(op)) {
				// 退出登录
				logout(request, response);
			}else if ("rebuildIndex".equals(op)) {
				// 重建索引
				rebuildIndex(request, response);
			}else{
				response.sendRedirect(request.getContextPath()+"/");
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("服务器忙");
		}
	}
	
	private void rebuildIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String token = request.getParameter("token");
		String sysRebuildIndexToken = sysDao.getProperty(SCProp.SYS_REBUILD_INDEX_TOKEN);
		
		if(sysRebuildIndexToken == null || sysRebuildIndexToken.trim().length() == 0){
			sysRebuildIndexToken = "5649497f8ded4b6e626a2c15";
			sysDao.setProperty(SCProp.SYS_REBUILD_INDEX_TOKEN, sysRebuildIndexToken);
		}
		DealResult dealResult = new DealResult(true,"操作成功,索引将在稍后被更新...");
		String result = request.getParameter("result");
		if(result.equals("success")){
			if(token.equals(sysRebuildIndexToken)){
				DocSearchUtil.rebuildIndex();
			}else{
				dealResult.setSuccess(false);
				dealResult.setDesc("操作未获得授权");
			}
		}else{
			AlertMailUtil.increaseFail();
			dealResult.setDesc("已收到失败反馈，将尽快与管理员取得联系");
		}
		
		String jsonResult = new Gson().toJson(dealResult);
		response.setContentType("application/json");
		response.getWriter().write(jsonResult);
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		response.setHeader("Refresh", "0;url=" + request.getContextPath() + "/index.jsp");
	}
	
	private void getLoginInfo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("loginFlag", true);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String adminName = request.getParameter("adminName");
		String adminPwd = request.getParameter("adminPwd");
		if(adminName == null || adminName.trim().length() == 0){
			redirectFail(request, response, "登录失败", "请输入用户名", "/login.jsp");
			return;
		}
		if(adminPwd == null || adminPwd.trim().length() == 0){
			redirectFail(request, response, "登录失败", "请输入密码", "/login.jsp");
			return;
		}
		String sysAdminName = sysDao.getProperty(SCProp.SYS_ADMIN_NAME);
		String sysAdminPwd = sysDao.getProperty(SCProp.SYS_ADMIN_PWD);
		if(sysAdminName == null || sysAdminName.trim().length() == 0
				|| sysAdminPwd == null || sysAdminPwd.trim().length() == 0){
			sysDao.setProperty(SCProp.SYS_ADMIN_NAME, adminName);
			sysDao.setProperty(SCProp.SYS_ADMIN_PWD, adminPwd);
			redirectSuccess(request, response, "管理员密码已重置", "您刚才输入的用户名密码已经被保存，现在请重新登录...", "/login.jsp");
			return;
		}
		
		if(sysAdminName.equals(adminName) && sysAdminPwd.equals(adminPwd)){
			request.getSession().setAttribute("sysAdminName", sysAdminName);
			request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
			return;
		}else{
			redirectFail(request, response, "登录失败", "用户名或密码错误", "/login.jsp");
			return;
		}
		
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
		request.getRequestDispatcher("/notification.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
