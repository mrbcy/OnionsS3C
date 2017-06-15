package tv.guanghe.datadev.s3c.filter;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
//全站编码解决过滤器
public class AllCharacterEncodingFilter implements Filter {
	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

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
		
		//读取指定的参数
		String encoding = filterConfig.getInitParameter("encoding");
		if(encoding==null){
			//没有配置参数，给一个默认值
			encoding = "UTF-8";
		}
		
		request.setCharacterEncoding(encoding);//只适合POST请求
		response.setCharacterEncoding(encoding);
		response.setContentType("text/html;charset="+encoding);
		MyServletRequest mrequest = new MyServletRequest(request);
		chain.doFilter(mrequest, response);
	}
	public void destroy() {

	}
}
class MyServletRequest extends HttpServletRequestWrapper{
	public MyServletRequest(HttpServletRequest request){
		super(request);
	}
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if(value==null)
			return null;
		if("get".equalsIgnoreCase(super.getMethod())){
			//get请求方式
			try {
				byte b[] = value.getBytes("ISO-8859-1");
				value = new String(b,super.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return value;
	}
	
}
