package com.fl.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fl.model.User;
import com.fl.service.UserService;
import com.fl.util.SessionTracker;

@RestController
public class LoginAPI {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public User authenticateUser( @RequestParam("loginId") String loginId,
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
		int hashCode = password.hashCode();
		
		User user;
		
		try {
			user = userService.getUser(loginId, ""+hashCode);
		} catch (Exception e) {
			throw new RuntimeException("Login failed");
		}
		
		if (user != null) {
			HttpSession currentSession = req.getSession();
			currentSession.setAttribute("loginId", loginId);
			SessionTracker.addSession(currentSession);
			res.addHeader("token", currentSession.getId());
			user.setToken(currentSession.getId());
			return user;
		}
		res.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		throw new RuntimeException("Login failed");
	}
	
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	
	public String logoutUser(HttpServletRequest req) {
		
		String authorizationHeader = req.getHeader(req.getHeader(HttpHeaders.AUTHORIZATION));
		
		HttpSession session = SessionTracker.getSession(authorizationHeader);

		if (session != null) {
			session.invalidate();
			return "logout success";
		}
		
		return "no valid session exists";
	}
	

}
