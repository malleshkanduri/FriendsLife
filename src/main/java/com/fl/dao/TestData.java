package com.fl.dao;

import java.util.ArrayList;
import java.util.List;

import com.fl.model.Category;

public class TestData {
	public List<Category> createCategories() {
		Category catCook = new Category("Cook" , "12345");
		Category catArts = new Category("Arts" , "22343");
		Category catSports = new Category("Sports" , "323234");
		
		List<Category> list = new ArrayList<Category>();
		
		list.add(catCook);
		list.add(catArts);
		list.add(catSports);
		
		return list;
	}
}
