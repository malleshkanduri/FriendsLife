package com.fl.listener;
 
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.fl.util.SessionTracker;
 
public class FlSessionListener implements HttpSessionListener {
	
    protected static Logger logger = Logger.getLogger("controller");
    private HttpSession session = null;
    
    public void sessionCreated(HttpSessionEvent event)
    {
        // no need to do anything here as connection may not have been established yet
        session  = event.getSession();
        logger.info("Session created for id " + session.getId());
    }
 
    public void sessionDestroyed(HttpSessionEvent event)  {
       session  = event.getSession();
	   SessionTracker.deleteSession(session.getId());
       
    }
}