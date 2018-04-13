package com.fl.model;

import java.util.List;

public class FriendDatePreference {

	String friendId;
	List<Day> days;

	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
}
