package com.p3.helpdesk;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class RestStart {
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}
	
	@RequestMapping("/new/")
	String home1() {
		return "Hello new World!";
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(RestStart.class, args);
	}

}