package com.fl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReqFilter implements Filter {

	private static final Logger logger = Logger.getLogger(ReqFilter.class);
	
	private static final boolean CONDITION = true;

	private static final String SECURE_URI_WITH_PATTERN = "fl";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("Initiating WebFilter >> ");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		

		HttpServletRequest req = (HttpServletRequest) request;
		
		logger.info("req.getRequestURL()" + req.getRequestURL());
		String uri = req.getRequestURI();
		String deleteLeadingSlashes = deleteLeadingSlashes(uri);
		logger.info("req.getRequestURL()" + req.getRequestURL());
		logger.info("deleteLeadingSlashes " + deleteLeadingSlashes);
		
		if (deleteLeadingSlashes.startsWith(SECURE_URI_WITH_PATTERN)) {

			if (CONDITION == true) {
				req.getPathInfo();
				/*HeaderMapRequestWrapper requestWrapper = new 
						HeaderMapRequestWrapper(req);*/
				 
				
				/*requestWrapper.addHeader("remote_addr", remote_addr);*/
	            // Goes to default servlet
				chain.doFilter(req, response); 
			} else {
				((HttpServletResponse) response)
					.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}	
		}
		
		chain.doFilter(req, response); 
		
	}
	
	private String deleteLeadingSlashes(String uri) {
		while (uri != null && uri.startsWith("/")) {
			uri = uri.replaceFirst("/", "");
		}
		while (uri != null && uri.startsWith("\\")) {
			uri = uri.replaceFirst("\\", "");
		}
		return uri;
	}

	@Override
	public void destroy() {
		logger.debug("Destroying WebFilter >> ");
	}
}