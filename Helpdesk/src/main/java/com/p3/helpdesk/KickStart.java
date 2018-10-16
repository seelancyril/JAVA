package com.p3.helpdesk;

import com.p3.helpdesk.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KickStart {

	@Autowired
	private ProductRepository rep;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(KickStart.class, args);
	}

	public void run(String... args) throws Exception{

//		System.out.println(rep.findOne("Archon"));

	}

}
