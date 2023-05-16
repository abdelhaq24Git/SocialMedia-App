package config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
@WebFilter("/*")
public class SessionTimeoutFilter implements Filter {
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if any
    }

	 @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	        HttpServletRequest httpRequest = (HttpServletRequest) request;
	        httpRequest.getSession().setMaxInactiveInterval(1800); // Set session timeout to 30 minutes
	        chain.doFilter(request, response);
	    }

    @Override
    public void destroy() {
        // Cleanup code, if any
    }
}
