package com.matheusmarques.neopdv.repository;


import com.matheusmarques.neopdv.domain.order.Order;
import com.matheusmarques.neopdv.domain.enums.StatusOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByStatus(StatusOrder statusOrder);

    Optional<Order> findByTicketAndStatus(int ticket, StatusOrder status);

    Optional<Order> findById(String id);
}
