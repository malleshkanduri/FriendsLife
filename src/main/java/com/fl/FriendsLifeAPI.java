package com.fl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.model.Friend;

@RestController
public class FriendsLifeAPI {
	@RequestMapping("/friend")
	public Friend getFriends() {
		Friend friend = new Friend("kanduri", 25, "Male");
		return friend;
	}
	
}