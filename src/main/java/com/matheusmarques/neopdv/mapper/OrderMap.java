package com.matheusmarques.neopdv.mapper;

import com.matheusmarques.neopdv.domain.Order;
import com.matheusmarques.neopdv.domain.enumerated.StatusOrder;
import com.matheusmarques.neopdv.dto.request.SalesStartRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderMap {
    public static Order map(SalesStartRequest request){
        Order order = new Order();
        order.setOwner(request.owner());
        order.setTableNumber(request.tableNumber());
        order.setAmount(new BigDecimal(0));
        order.setStatus(StatusOrder.OPEN);
        order.setCreatedDate(LocalDateTime.now());
        order.setItemsId(new ArrayList<>());
        return order;
    }
}
