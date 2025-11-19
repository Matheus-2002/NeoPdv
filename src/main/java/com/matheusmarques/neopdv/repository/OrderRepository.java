package com.matheusmarques.neopdv.repository;


import com.matheusmarques.neopdv.domain.order.Order;
import com.matheusmarques.neopdv.domain.enums.StatusOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findByTableNumberAndStatus(int tableNumber, StatusOrder status);

    Optional<Order> findById(String id);
}
