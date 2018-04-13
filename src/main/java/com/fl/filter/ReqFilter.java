package com.fl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.fl.util.SessionTracker;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReqFilter implements Filter {

	private static final Logger logger = Logger.getLogger(ReqFilter.class);
	private static final String SECURE_URI_WITH_PATTERN = "fl";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("Initiating WebFilter >> ");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		String authorizationa = req.getHeader(HttpHeaders.AUTHORIZATION);
		logger.info("Authrization header" + authorizationa);
		StringBuffer requestURL = req.getRequestURL();
		logger.info(requestURL);
		
		String uri = req.getRequestURI();
		String deleteLeadingSlashes = deleteLeadingSlashes(uri);

		logger.info("URI " + uri + " After Deleting leading slashes" + deleteLeadingSlashes);
		
		// validate security for url pattern SECURE_URI_WITH_PATTERN
		if (deleteLeadingSlashes.startsWith(SECURE_URI_WITH_PATTERN)) {
			
			HttpSession session = null;
			
			String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
			if (authorization  != null) {
				session = SessionTracker.getSession(authorization);
			}
			
			if (session == null) {
				// invalid or token expired.. send bad request error
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			String id = (String)session.getAttribute("loginId");
			logger.info("loginId  from session " +  id);
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