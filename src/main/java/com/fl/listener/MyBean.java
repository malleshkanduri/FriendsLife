package com.fl.listener;

import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MyBean {
	
	@Bean
	public HttpSessionListener httpSessionListener() {
	    return new FlSessionListener(); 
	}
}