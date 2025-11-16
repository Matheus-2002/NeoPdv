package com.matheusmarques.neopdv.mapper;

import com.matheusmarques.neopdv.domain.Order;
import com.matheusmarques.neopdv.domain.OrderItem;
import com.matheusmarques.neopdv.domain.enumerated.StatusOrder;
import com.matheusmarques.neopdv.domain.enumerated.StatusTable;
import com.matheusmarques.neopdv.dto.request.SalesStartRequest;
import com.matheusmarques.neopdv.dto.response.OrderResponse;
import com.matheusmarques.neopdv.dto.response.SalesStartResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderMap {

    private static String START_ORDER_SUCESS = "A ordem foi iniciada com sucesso, já podem ser lançandos produtos a ela";

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

    public static SalesStartResponse toSaleStartResponse(Order order){
        return new SalesStartResponse(
                true,
                START_ORDER_SUCESS,
                order.getId(),
                StatusTable.FREE,
                order.getCreatedDate()
        );
    }

    public static OrderResponse toOrderResponse(Order order, List<OrderItem> itemList){
        return new OrderResponse(
                order.getId(),
                order.getOwner(),
                order.getTableNumber(),
                order.getAmount(),
                order.getPaymentMethod(),
                order.getStatus(),
                order.getCreatedDate(),
                itemList
        );
    }
}
