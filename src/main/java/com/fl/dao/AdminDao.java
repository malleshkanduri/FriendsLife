package com.fl.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.fl.model.Category;
import com.fl.model.Friend;
import com.fl.model.FriendDatePreference;

public interface AdminDao {
	List<Category> getCategories();
	List<Category> getCategories(LocalDate date);
	List<Friend> getFriends();
	Friend getFriend(String id);
	FriendDatePreference getFriendDatePreference(String id);
}
