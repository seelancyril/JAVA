package com.see.leanring;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RestDemoService {
	
	List<User> users = Arrays.asList(
			new User("Seelan","see@gmail.com",1234),
			new User("Seelan","see@gmail.com",1234)
			);
	
	public List<User> getAllUsers() {
		return users;
	}
}
