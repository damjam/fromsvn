package flink.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;

public class EncodingFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;
	private FilterConfig filterConfig;
    private String encoding;	
	
	
	public void destroy() {
		super.destroy(); 
	}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        try {
        	String encode = request.getParameter("encode");
        	
        	if (StringUtils.isNotEmpty(encode)) {
        		request.setCharacterEncoding(encode);
        	}
        	else {
        		request.setCharacterEncoding(this.encoding);
        	}
        	
            filterChain.doFilter(request, response);
        } catch (ServletException sx) {
            filterConfig.getServletContext().log(sx.getMessage());
        } catch (IOException iox) {
            filterConfig.getServletContext().log(iox.getMessage());
        }
    }	

    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
        this.filterConfig = filterConfig;
    }    
}
