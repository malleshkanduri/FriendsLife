package com.fl.app;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@RequestMapping("/fl")
public class FriendsLifeAPI {

	@Autowired
	private FriendsLifeService service;
	
	Logger logger = Logger.getLogger(FriendsLifeAPI.class);
	
	@RequestMapping("/category") 
	public List<Category> getAllCategories() {
		logger.info("service " + service);
		return service.getCategories();
	}
	
	@PostMapping("/categoriesByDays")
	public List<Category> getCategoriesByDays(@RequestBody Days days) {
		return service.getCategoriesByDays(days.getDays());
	}
	
	@RequestMapping("/friends")
	public List<Friend> getFriends() {
		return service.getFriends();
	}
	@PostMapping("/createFriend")
	public String createFriend(@RequestBody Friend friend) throws FriendExistException {
		return service.createFriend(friend);
	}
	
	@PostMapping("/friendDays")
	public Days getFriendDays(@RequestBody String friendId) {
		return null;
	}
}