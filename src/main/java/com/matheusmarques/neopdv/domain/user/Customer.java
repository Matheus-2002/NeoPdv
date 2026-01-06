package com.matheusmarques.neopdv.domain.user;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "tb_customer")
public class Customer {

    @MongoId
    private String id;
    private String name;
}
