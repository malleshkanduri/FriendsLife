package com.fl.model;

import java.io.Serializable;

public class Category implements Serializable {

	private static final long serialVersionUID = 2228514779598193016L;
	
	String id;
	String name;
	boolean active;
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Category(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Category() {}
}
