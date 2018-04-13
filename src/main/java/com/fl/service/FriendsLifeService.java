package com.fl.service;

import java.util.List;

import com.fl.model.Category;
import com.fl.model.Day;
import com.fl.model.Friend;
import com.fl.model.FriendDayPreferences;
import com.fl.model.FriendExistException;

public interface FriendsLifeService {
	List<Category> getCategories();
	List<Category> getCategoriesByDays(List<Day> days);
	List<Friend> getFriends();
	String createFriend(Friend friend) throws FriendExistException;
	
	String updateFriend(Friend friend);
	String createFriendDayPreferences(FriendDayPreferences frndDayPref);
	List<Day> getFriendDayPreference(String frndId);
}

