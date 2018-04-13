package com.fl.app;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.model.Category;
import com.fl.model.Days;
import com.fl.model.Friend;
import com.fl.model.FriendExistException;
import com.fl.service.FriendsLifeService;

@RestController
@RequestMapping("/fl")
public class FriendsLifeAPI {

	@Autowired
	private FriendsLifeService service;
	
	Logger logger = Logger.getLogger(FriendsLifeAPI.class);
	
	@RequestMapping("/category") 
	public List<Category> getAllCategories(HttpServletResponse response) {
		logger.info("service " + service);
//		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Vary", "Origin");
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		return service.getCategories();
	}
	
	@PostMapping("/categoriesByDays")
	public List<Category> getCategoriesByDays(@RequestBody Days days) {
		return service.getCategoriesByDays(days.getDays());
	}
	
	@RequestMapping("/friends")
	public List<Friend> getFriends(HttpServletResponse response) {
		response.addHeader("Vary", "Origin");
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		return service.getFriends();
	}
	@PostMapping("/createFriend")
	public String createFriend(@RequestBody Friend friend) throws FriendExistException {
		return service.createFriend(friend);
	}
	
	@PostMapping("/updateFriend")
	public String updateFriend(@RequestBody Friend friend) throws FriendExistException {
		return service.updateFriend(friend);
	}
	
	@PostMapping("/friendDays")
	public Days getFriendDays(@RequestBody String friendId) {
		return null;
	}
}