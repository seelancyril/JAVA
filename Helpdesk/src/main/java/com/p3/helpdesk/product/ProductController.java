package com.p3.helpdesk.product;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products", method = RequestMethod.GET)
public class ProductController {
	
	private final ProductRepository rep;

	public ProductController(ProductRepository rep) {
		this.rep = rep;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Product> getAllProducts() {
		return rep.findAll();
	}
}
