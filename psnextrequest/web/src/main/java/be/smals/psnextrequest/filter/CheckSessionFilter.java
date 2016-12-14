package be.smals.psnextrequest.filter;

import be.smals.psnextrequest.bean.SessionBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckSessionFilter implements Filter {

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
