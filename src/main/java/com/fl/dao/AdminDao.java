package com.fl.dao;

import java.util.List;

import com.fl.model.Category;
import com.fl.model.Day;
import com.fl.model.Friend;
import com.fl.model.FriendExistException;
import com.fl.model.User;

public interface AdminDao {
	List<Category> getCategories();
	List<Category> getCategories(List<Day> days);

	List<Friend> getFriends();
	Friend getFriend(String id);
	String updateFriend(Friend friend);
	String createFriend(Friend friend) throws FriendExistException;
	
	List<Day> getFriendDayPreference(String id);
	String createFriendDayPreferences(String frndId, List<Day> dayPrefs);
	
	

	User getUser(String loginId, String passwprd);
}
