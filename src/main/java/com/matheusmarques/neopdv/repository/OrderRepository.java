package com.matheusmarques.neopdv.repository;


import com.matheusmarques.neopdv.domain.Order;
import com.matheusmarques.neopdv.domain.enumerated.StatusOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, Long> {

    Optional<Order> findByTableNumberAndStatus(int tableNumber, StatusOrder status);
}
