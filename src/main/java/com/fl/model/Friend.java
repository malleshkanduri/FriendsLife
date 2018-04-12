package com.fl.model;

import java.io.Serializable;

public class Friend implements Serializable {
	String name;
	String sex;
	int age;
	
	public Friend(String name, int age, String sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "Friend [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
}
