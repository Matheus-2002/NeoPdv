package com.matheusmarques.neopdv.repository;

import com.matheusmarques.neopdv.domain.order.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {

    List<OrderItem> findByOrderIdAndProductId(String orderId, String productId);

}
