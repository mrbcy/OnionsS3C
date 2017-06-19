package tv.guanghe.datadev.s3c.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminAuthFilter implements Filter {
	private FilterConfig filterConfig;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
	 	HttpServletRequest  request;
        HttpServletResponse response;
        
        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) res;
        } catch (ClassCastException e) {
            throw new ServletException("non-HTTP request or response");
        }
		try{
			String adminName = (String) request.getSession().getAttribute("sysAdminName");
			if(adminName == null || adminName.trim().length() == 0){
				response.setHeader("Refresh", "0;url=" + request.getContextPath() + "/login.jsp");
				return;
			}
			chain.doFilter(request, response);
		}catch (Exception e) {
			e.printStackTrace();
			response.setHeader("Refresh", "0;url=" + request.getContextPath() + "/login.jsp");
			return;
		}
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
