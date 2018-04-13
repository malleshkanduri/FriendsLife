package com.fl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fl.model.Category;
import com.fl.model.Day;
import com.fl.model.Friend;
import com.fl.model.FriendDatePreference;
import com.fl.model.FriendExistException;

@Repository
public class AdminDaoImpl implements AdminDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private String GET_ALL_CATEGORIES="SELECT id, name, picture, active FROM public.categories";

	private String GET_CATEGORIES_BY_DAYS="select CAT.* from public.days D \n" + 
			"INNER JOIN public.class_days CD ON CD.day_id = D.id \n" + 
			"INNER JOIN public.classes C ON C.id = CD.class_id \n" + 
			"INNER JOIN public.categories CAT ON CAT.id = C.category_id \n" + 
			"where ";
	
	private String CREATE_FRIEND = "INSERT INTO public.friends(first_name, last_name, nick_name, created_at, updated_at)\r\n" + 
			"	VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
	
	private String UPDATE_FRIEND = "UPDATE public.friends set first_name = ?, last_name = ?, nick_name = ? where id = ?";

	private String GET_FRIEND_ID_BY_NAME = "SElECT ID FROM public.friends WHERE first_name = ? and last_name = ? and nick_name = ? ";
	
	private String GET_ALL_FRIENDs = "SElECT * FROM public.friends";
	
	private String GET_FRIEND_BY_ID = "SElECT * FROM public.friends WHERE id = ?";
	
	
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
	public List<Category> getCategories(List<Day> days) {
		StringBuilder whereClause = new StringBuilder();
		boolean isNotFirst = false;
		for( Day day : days) {
			if(isNotFirst) {
				whereClause.append(" or ");
			}
			whereClause.append("(day = '");
			whereClause.append(day.getDayOfTheWeek());
			whereClause.append("' and slot = '");
			whereClause.append(day.getAmPm());
			whereClause.append("')");
			isNotFirst = true;
		}
		return jdbcTemplate.query(GET_CATEGORIES_BY_DAYS + whereClause.toString(), new CategoryMapper());
	}

	@Override
	public List<Friend> getFriends() {
		return jdbcTemplate.query(GET_ALL_FRIENDs, new FriendMapper());
	}

	@Override
	public Friend getFriend(String id) {
		Object[] inputs = {id};
		int[] types = {Types.NUMERIC};
		return jdbcTemplate.queryForObject(GET_FRIEND_BY_ID, inputs, types, new FriendMapper());
	}

	@Override
	public FriendDatePreference getFriendDatePreference(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String createFriend(Friend friend) throws FriendExistException {
		Object[] inputs = {friend.getFirstName(), friend.getLastName(), friend.getNickName()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
		try {
			String frndId = jdbcTemplate.queryForObject(GET_FRIEND_ID_BY_NAME, inputs, types, String.class);
			if (frndId == null || frndId.trim().length() > 0) {
				throw new FriendExistException("Friend Existing with given details");
			}
		} catch (EmptyResultDataAccessException e) {}
		jdbcTemplate.update(CREATE_FRIEND, inputs, types);
		return jdbcTemplate.queryForObject(GET_FRIEND_ID_BY_NAME, inputs, types, String.class);
	}
	@Override
	public String updateFriend(Friend friend) {
		Object[] inputs = {friend.getFirstName(), friend.getLastName(), friend.getNickName(), friend.getId()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
		jdbcTemplate.update(UPDATE_FRIEND, inputs, types);
		return String.valueOf(friend.getId());
	}
	

	@Override
	public String createFriendDayPreferences(int frndId, List<Day> dayPrefs) {
		return null;
	}

	@Override
	public List<Day> getFriendDays(String friendId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static final class FriendMapper implements RowMapper<Friend> {
	    @Override
	    public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Friend friend = new Friend();
	        friend.setId(rs.getInt("id"));
	        friend.setFirstName(rs.getString("first_name"));
	        friend.setLastName(rs.getString("last_name"));
	        friend.setNickName(rs.getString("nick_name"));
	        return friend;
	    }
	}
	
}