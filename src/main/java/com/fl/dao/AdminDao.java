package com.fl.dao;

import java.util.List;

import com.fl.model.Category;
import com.fl.model.Day;
import com.fl.model.Friend;
import com.fl.model.FriendDatePreference;
import com.fl.model.FriendExistException;

public interface AdminDao {
	List<Category> getCategories();
	List<Category> getCategories(List<Day> days);
	List<Friend> getFriends();
	Friend getFriend(String id);
	FriendDatePreference getFriendDatePreference(String id);
	String createFriend(Friend friend) throws FriendExistException;
	List<Day> getFriendDays(String friendId);
}
