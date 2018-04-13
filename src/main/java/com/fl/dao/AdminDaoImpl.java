package com.fl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fl.model.Category;
import com.fl.model.Friend;
import com.fl.model.FriendDatePreference;

@Repository
public class AdminDaoImpl implements AdminDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	static TestData testdata = new TestData();
	
	private String GET_ALL_CATEGORIES="SELECT id, name, picture, active FROM public.categories";

	
	@Override
	public List<Category> getCategories() {
		return jdbcTemplate.query(GET_ALL_CATEGORIES, new CategoryMapper());
	}
	
	private static final class CategoryMapper implements RowMapper<Category> {
	    @Override
	    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Category employee = new Category();
	        employee.setId(rs.getString("id"));
	        employee.setName(rs.getString("name"));
	        employee.setActive(rs.getBoolean("active"));
	        return employee;
	    }
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