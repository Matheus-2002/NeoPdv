package com.matheusmarques.neopdv.service;

import com.matheusmarques.neopdv.domain.Order;
import com.matheusmarques.neopdv.domain.enumerated.StatusTable;
import com.matheusmarques.neopdv.dto.request.SalesStartRequest;
import com.matheusmarques.neopdv.dto.response.SalesStartResponse;
import com.matheusmarques.neopdv.mapper.OrderMap;
import com.matheusmarques.neopdv.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository){
        this.repository = repository;
    }

    public SalesStartResponse orderStart(SalesStartRequest request){
        try {
            Order order = OrderMap.map(request);
            Order orderSave = repository.save(order);
            return new SalesStartResponse(
                    true,
                    "Tudo certo",
                    order.getId(),
                    StatusTable.FREE,
                    LocalDateTime.now()
            );
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
