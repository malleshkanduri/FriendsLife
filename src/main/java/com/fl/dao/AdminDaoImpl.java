package com.fl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fl.model.Category;
import com.fl.model.Day;
import com.fl.model.Friend;
import com.fl.model.FriendDatePreference;
import com.fl.model.User;

@Repository
public class AdminDaoImpl implements AdminDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	static TestData testdata = new TestData();
	
	private String GET_ALL_CATEGORIES="SELECT id, name, picture, active FROM public.categories";

	private String GET_CATEGORIES_BY_DAYS="select CAT.* from public.days D \n" + 
			"INNER JOIN public.class_days CD ON CD.day_id = D.id \n" + 
			"INNER JOIN public.classes C ON C.id = CD.class_id \n" + 
			"INNER JOIN public.categories CAT ON CAT.id = C.category_id \n" + 
			"where ";
	
	private String CREATE_FRIEND = "INSERT INTO public.friends(first_name, last_name, nick_name, created_at, updated_at)\r\n" + 
			"	VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

	private String GET_USER = "SELECT * from public.users where username = ? and password = ?";
	
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
	@Override
	public int createFriend(Friend friend) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {           
		                @Override
		                public PreparedStatement createPreparedStatement(Connection connection)
		                        throws SQLException {
		                    PreparedStatement ps = connection.prepareStatement(CREATE_FRIEND,
		                        Statement.RETURN_GENERATED_KEYS); 
		                    ps.setString(1, friend.getFirstName());
		                    ps.setString(2, friend.getLastName());
		                    ps.setString(3, friend.getNickName());
		                    return ps;
		                }
		            }, holder);

		Long newPersonId = holder.getKey().longValue();
		return newPersonId.intValue();
	}

	@Override
	public List<Day> getFriendDays(String friendId) {
		// TODO Auto-generated method stub
		return null;
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
	
}