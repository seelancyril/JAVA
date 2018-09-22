package com.see.leanring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestDemoController {
	
	@Autowired
	private RestDemoService user;
	
	@RequestMapping("/user")
	public List<User> getAllUsers(){
		return user.getAllUsers();
	}
	
}
