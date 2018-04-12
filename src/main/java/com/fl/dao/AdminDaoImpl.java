package com.fl.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.fl.model.Category;
import com.fl.model.Friend;
import com.fl.model.FriendDatePreference;

@Repository
public class AdminDaoImpl implements AdminDao {

	static TestData testdata = new TestData();
	
	@Override
	public List<Category> getCategories() {
		//test implementation
		return testdata.createCategories();
	}

	@Override
	public List<Category> getCategories(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Friend> getFriends() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Friend getFriend(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FriendDatePreference getFriendDatePreference(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}