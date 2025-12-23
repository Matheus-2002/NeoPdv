package com.matheusmarques.neopdv.mapper.order;

import com.matheusmarques.neopdv.api.order.response.OrderCardResponse;
import com.matheusmarques.neopdv.domain.order.Order;
import com.matheusmarques.neopdv.domain.order.OrderItem;
import com.matheusmarques.neopdv.domain.enums.StatusOrder;
import com.matheusmarques.neopdv.domain.enums.StatusTable;
import com.matheusmarques.neopdv.api.order.request.SalesStartRequest;
import com.matheusmarques.neopdv.api.order.response.OrderResponse;
import com.matheusmarques.neopdv.api.order.response.SalesStartResponse;

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

    public static List<OrderCardResponse> toOrderCardResponse(List<Order> orders){
        List<OrderCardResponse> responseList = new ArrayList<>();
        for (Order order: orders){
            OrderCardResponse response = new OrderCardResponse(
                    order.getTableNumber(),
                    order.getCustomer(),
                    order.getCreatedDate(),
                    order.getStatus(),
                    order.getAmount()
            );
            responseList.add(response);
        }
        return responseList;
    }
}
