package com.matheusmarques.neopdv.repository;

import com.matheusmarques.neopdv.domain.user.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
