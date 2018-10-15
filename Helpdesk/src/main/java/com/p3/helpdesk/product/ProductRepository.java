package com.p3.helpdesk.product;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    public List<Product> findProduct(String name);

}
