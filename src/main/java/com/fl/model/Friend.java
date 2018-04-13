package com.fl.model;

import java.io.Serializable;

public class Friend implements Serializable {

	private static final long serialVersionUID = 377093108996580864L;
	
	String firstName;
	String lastName;
	String nickName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
