package com.matheusmarques.neopdv.repository;


import com.matheusmarques.neopdv.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Long> {
    List<Order> findByOwner(String owner);
}
