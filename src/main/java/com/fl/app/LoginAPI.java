package com.fl.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fl.util.SessionTracker;

@RestController
public class LoginAPI {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String authenticateUser( @RequestParam("loginId") String loginId,
									@RequestParam("password") String password,
									HttpServletRequest req ,
									HttpServletResponse res  ) {
		
		System.out.println("loginId" + loginId + " password " + password);
		
		HttpSession oldSession = req.getSession(false);
		if (oldSession != null) {
			String id = oldSession.getId();
			SessionTracker.deleteSession(id);
			oldSession.invalidate();
		}
		
		HttpSession currentSession = req.getSession();
		currentSession.setAttribute("loginId", loginId);
		SessionTracker.addSession(currentSession);
		
		res.addHeader("token", currentSession.getId());
		res.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		
		
		return "login success";
	}
	
/*	@RequestMapping(value="/logout", method=RequestMethod.GET)
	
	public String logoutUser(HttpServletRequest req ,
									HttpServletResponse res  ) {
		
		
		HttpSession oldSession = req.getSession(false);
		if (oldSession != null) {
			String id = oldSession.getId();
			SessionTracker.deleteSession(id);
			oldSession.invalidate();
		}
		
		HttpSession currentSession = req.getSession();
		currentSession.setAttribute("loginId", loginId);
		SessionTracker.addSession(currentSession);
		
		res.addHeader("token", currentSession.getId());
		
		return "login success";
	}*/
	

}
