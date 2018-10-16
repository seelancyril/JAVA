package com.p3.helpdesk.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductImpl{
	@Autowired
	private MongoTemplate template;
	
	public List<Product> getAllProducts() {
		return template.findAll(Product.class);
	}
	
}
