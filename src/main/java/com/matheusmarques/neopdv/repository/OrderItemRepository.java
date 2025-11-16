package com.matheusmarques.neopdv.repository;

import com.matheusmarques.neopdv.domain.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {

}
