package com.p3.helpdesk.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class Product {

    @Id
    private String id;

    private String prod_name;
    private String prod_desc;

    public Product(){};

    public Product(String prod_name, String prod_desc){
        this.prod_desc = prod_desc;
        this.prod_name = prod_name;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_desc() {
        return prod_desc;
    }

    public void setProd_desc(String prod_desc) {
        this.prod_desc = prod_desc;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, prod_name, prod_desc);
    }
}
