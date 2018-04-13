package com.fl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl.dao.AdminDaoImpl;
import com.fl.model.User;

@Service
public class UserService {
	
	@Autowired
	private AdminDaoImpl dao;
	
	public User getUser(String loginId, String password) {
		return dao.getUser(loginId, password);
	}
}
