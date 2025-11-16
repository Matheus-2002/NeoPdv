package com.matheusmarques.neopdv.service;

import com.matheusmarques.neopdv.domain.Order;
import com.matheusmarques.neopdv.domain.enumerated.StatusOrder;
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

    private static final String TABLE_OCCUPIED_MESSAGE = "A mesa já está ocupada, escolha outra.";

    public OrderService(OrderRepository repository){
        this.repository = repository;
    }

    public SalesStartResponse orderStart(SalesStartRequest request){
        Order orderRequest = OrderMap.map(request);
        if (repository.findByTableNumberAndStatus(orderRequest.getTableNumber(), StatusOrder.OPEN).isPresent()){
            return new SalesStartResponse(
                    false,
                    TABLE_OCCUPIED_MESSAGE,
                    null,
                    StatusTable.OCCUPIED,
                    LocalDateTime.now()
            );
        }
        Order orderSave = repository.save(orderRequest);
        return OrderMap.toSalesStarResponse(orderSave);
    }

}
