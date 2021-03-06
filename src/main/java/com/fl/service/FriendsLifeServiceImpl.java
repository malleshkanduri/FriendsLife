package com.fl.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fl.dao.AdminDaoImpl;
import com.fl.model.Category;
import com.fl.model.Day;
import com.fl.model.Friend;
import com.fl.model.FriendDayPreferences;
import com.fl.model.FriendExistException;

@Service
public class FriendsLifeServiceImpl implements FriendsLifeService {

	@Autowired
	private AdminDaoImpl dao;
	
	private Logger logger = Logger.getLogger(FriendsLifeServiceImpl.class);
	
	@Override
	public List<Category> getCategories() {
		logger.info("dao "  + dao );
		return dao.getCategories();
	}

	@Override
	public List<Friend> getFriends() {
		return dao.getFriends();
	}

	@Override
	public List<Category> getCategoriesByDays(List<Day> days) {
		return dao.getCategories(days);
	}

	@Override
	public String createFriend(Friend friend) throws FriendExistException {
		return dao.createFriend(friend);
	}

	@Override
	public String updateFriend(Friend friend) {
		return dao.updateFriend(friend);
	}
	
	@Override
	public String createFriendDayPreferences(FriendDayPreferences frndDayPref) {
		return dao.createFriendDayPreferences(frndDayPref.getFriendId(), frndDayPref.getDays());
	}
	
	@Override
	public List<Day> getFriendDayPreference(String frndId) {
		return dao.getFriendDayPreference(frndId);
	}
	
	
}
