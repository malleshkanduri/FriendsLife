package com.fl.service;

import java.util.List;

import com.fl.model.Category;
import com.fl.model.Day;
import com.fl.model.Friend;

public interface FriendsLifeService {
	List<Category> getCategories();
	List<Category> getCategoriesByDays(List<Day> days);
	List<Friend> getFriends();
}
