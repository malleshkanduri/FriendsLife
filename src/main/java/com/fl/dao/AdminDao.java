package com.fl.dao;

import java.util.List;

import com.fl.model.Category;
import com.fl.model.Day;
import com.fl.model.Friend;
import com.fl.model.FriendDatePreference;
import com.fl.model.User;
import com.fl.model.FriendExistException;

public interface AdminDao {
	List<Category> getCategories();
	List<Category> getCategories(List<Day> days);

	List<Friend> getFriends();
	Friend getFriend(String id);
	String updateFriend(Friend friend);
	String createFriend(Friend friend) throws FriendExistException;
	
	FriendDatePreference getFriendDatePreference(String id);
	List<Day> getFriendDays(String friendId);
	User getUser(String loginId, String passwprd);
	String createFriendDayPreferences(int frndId, List<Day> dayPrefs);
}
