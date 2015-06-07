package be.smals.psnextrequest.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodeFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;


	public void destroy() {
		this.filterConfig = null;	
	}


	public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Setting the character set for the request
		request.setCharacterEncoding("ISO-8859-1");

		// pass the request on
		chain.doFilter(request, response);

		// Setting the character set for the response
		response.setContentType("text/html; charset=ISO-8859-1");
	}


	public void init(final FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;	
	}

}