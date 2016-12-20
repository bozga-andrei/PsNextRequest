package be.smals.psnextrequest.filter;


import javax.servlet.*;
import java.io.IOException;

public class EncodeFilter implements Filter {
    @SuppressWarnings("unused")
    private FilterConfig filterConfig = null;


    public void destroy() {
        this.filterConfig = null;
    }


    public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Setting the character set for the request
        request.setCharacterEncoding("UTF-8");

        // pass the request on
        chain.doFilter(request, response);

        // Setting the character set for the response
        response.setContentType("text/html; charset=UTF-8");
    }


    public void init(final FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

}