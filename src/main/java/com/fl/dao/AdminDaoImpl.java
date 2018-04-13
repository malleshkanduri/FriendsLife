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
import com.fl.model.FriendDayPreferences;
import com.fl.model.FriendExistException;
import com.fl.model.User;

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

	private String GET_USER = "SELECT * from public.users where username = ? and password = ?";
	private String GET_FRIEND_ID_BY_NAME = "SElECT ID FROM public.friends WHERE first_name = ? and last_name = ? and nick_name = ? ";
	
	private String GET_ALL_FRIENDs = "SElECT * FROM public.friends";
	
	private String GET_FRIEND_BY_ID = "SElECT * FROM public.friends WHERE id = ?";
	
	private String CREATE_FRIEND_DAY_PREF = "INSERT INTO public.friend_days( created_at, updated_at, day_id, friend_id) "
			+ "VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM public.days where day = ? and slot =?), ?)";
	

	private String DELETE_FRIEND_DAY_PREF = "DELETE from public.friend_days where friend_id = ?";
	
	private String GET_FRND_DAY_PREF = "SELECT id, day, slot FROM public.days D\r\n" + 
			"inner join public.friend_days FD on FD.day_id = D.id\r\n" + 
			"where fd.friend_id = ?";
	
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
	        employee.setPicture(rs.getString("picture"));
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
	public List<Day> getFriendDayPreference(String id) {
		
		Object[] inputs = {id};
		int[] types = {Types.NUMERIC};
		return jdbcTemplate.query(GET_FRND_DAY_PREF, inputs, types, new DayMapper());
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
	public String createFriendDayPreferences(String frndId, List<Day> dayPrefs) {
		Object[] inputs1 = {frndId};
		int[] types1 = {Types.NUMERIC};
		jdbcTemplate.update(DELETE_FRIEND_DAY_PREF, inputs1, types1);
		for( Day day : dayPrefs) {
			Object[] inputs = {day.getDayOfTheWeek(), day.getAmPm(), frndId};
			int[] types = {Types.VARCHAR, Types.VARCHAR, Types.NUMERIC};
			jdbcTemplate.update(CREATE_FRIEND_DAY_PREF, inputs, types);
		}
		return frndId;
	}
	
	@Override
	public User getUser(String loginId, String password) {
		
		Object[] inputs = {loginId, password};
		int[] type = {Types.VARCHAR, Types.VARCHAR};
		
		return jdbcTemplate.queryForObject(GET_USER, inputs, type, new userMapper());
		
		/*return jdbcTemplate.query(new PreparedStatementCreator() {           
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(CREATE_FRIEND,
                    Statement.RETURN_GENERATED_KEYS); 
                ps.setString(1, loginId);
                ps.setString(2, password);
                return ps;
            }
        },  new userMapper());*/
		//return jdbcTemplate.query(GET_USER + whereClause.toString(), new CategoryMapper());
	}
	
	private static final class userMapper implements RowMapper<User> {
	    @Override
	    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	
	    	User user = new User();
		    user.setUsername(rs.getString("username"));
		    user.setid(rs.getString("id"));
		        
	        return user;
	    }
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
	
	private static final class DayMapper implements RowMapper<Day> {
	    @Override
	    public Day mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Day friend = new Day();
	        friend.setId(rs.getString("id"));
	        friend.setDayOfTheWeek(rs.getString("day"));
	        friend.setAmPm(rs.getString("slot"));
	        return friend;
	    }
	}
	
}