package com.fl.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class FriendsLifeController {

	/*@Value("${welcome.message:test}")
	private String message = "Hello World";
*/
	@RequestMapping("/fl/testjsp")
	public String welcome(Map<String, Object> model) {
		//model.put("message", this.message);
		return "welcome";
	}
	
	@RequestMapping(value="/login-old", method=RequestMethod.GET)
	
	public String authenticateUser( @RequestParam("loginId") String loginId,
									@RequestParam("password") String password,
									Model model, HttpServletRequest req) {
		System.out.println("loginId" + loginId + " password " + password);
		
		HttpSession oldSession = req.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate();
		}
		HttpSession currentSession = req.getSession();
		currentSession.setAttribute("loginId", loginId);
		
		model.addAttribute("loginId_value", loginId);
		
		return "loginsuccess";
	}
	
	public void validate () {
		/*if ( request.getSession().getAttribute("user")==null) {  
		    System.out.println("hello");
		     return "redirect:/login.html"
		    }
		else 
		   return "index";
		}*/
	}
}