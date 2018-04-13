package com.fl.app;

import java.util.ArrayList;
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
	
	@RequestMapping("/categories") 
	public List<Category> getCategories() {
		List<Category> cat = service.getCategories();
		System.out.println("Categories " + cat);
		System.out.println("Categories " + cat.size());
		
		cat = new ArrayList<>();
		cat.add(new Category("9", "New Cat"));
		cat.add(new Category("10", "New Cat 2"));
		cat.add(new Category("11", "New Cat 3"));
		
		
		
		return cat;
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
	public Friend createFriend(@RequestBody Friend friend) {
		return friend;
	}
	
	@PostMapping("/createFriend2")
	public String createFriend2(@RequestBody String friend) {
		return friend;
	}
	
	@PostMapping("/friendDays")
	public String getFriendDays(@RequestBody String friendId) {
		return friendId;
	}
}