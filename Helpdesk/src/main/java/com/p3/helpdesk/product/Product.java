package com.p3.helpdesk.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {

    @Id
    private String id;

    private String name;
    private String desc;

    public Product(){};

    public Product(String prod_name, String prod_desc){
        this.desc = prod_desc;
        this.name = prod_name;
    }

    public String getProd_name() {
        return name;
    }

    public void setProd_name(String prod_name) {
        this.name = prod_name;
    }

    public String getProd_desc() {
        return desc;
    }

    public void setProd_desc(String prod_desc) {
        this.desc = prod_desc;
    }

}
