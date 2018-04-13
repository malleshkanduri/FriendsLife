package com.fl.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Keep track of sessions..
 */
public class SessionTracker {
		static Logger logger = Logger.getLogger(SessionTracker.class);
		static final Map<String, HttpSession> sessionMap = new HashMap<>();
		
		static public HttpSession getSession(String sessionId) {
			return sessionMap.get(sessionId);
		}
		
		static public void addSession(HttpSession session) {
			sessionMap.put(session.getId(), session);
		}
		
		static public void deleteSession(String sessionId) {
			logger.info("Deleting session "  + sessionId);
			HttpSession remove = sessionMap.remove(sessionId);
			if(remove != null) {
				logger.info("Session deleted");
			} else {
				logger.info("Failed to delete session");
			}
		}
}
