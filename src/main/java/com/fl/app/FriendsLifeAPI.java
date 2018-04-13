package com.fl.app;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.model.Category;
import com.fl.service.FriendsLifeService;

@RestController
@RequestMapping("/fl")
public class FriendsLifeAPI {

	@Autowired
	private FriendsLifeService service;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	Logger logger = Logger.getLogger(FriendsLifeAPI.class);
	
	@RequestMapping("/category") 
	public List<Category> getAllCategories() {
		logger.info("service " + service);
		return service.getCategories();
	}
	
	@RequestMapping("/friends")
	public String getFriends() {
		//System.out.println("service value" + service.getClass());
		return "Hellow" + jdbcTemplate;
	}
	
}