package be.smals.psnextrequest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.smals.psnextrequest.bean.SessionBean;

public class CheckSessionFilter implements Filter{

	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;
	
	
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		SessionBean sessionBean = (SessionBean) req.getSession().getAttribute("sessionBean");

		if (sessionBean == null || !sessionBean.isLoggedIn()) {
		    res.sendRedirect(req.getContextPath() + "/faces/public/login.xhtml");
		} else {
		    chain.doFilter(request, response);
		}
	}

	public void destroy() {
		this.filterConfig = null;
	}

}
