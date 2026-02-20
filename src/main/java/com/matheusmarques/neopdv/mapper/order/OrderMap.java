package com.matheusmarques.neopdv.mapper.order;

import com.matheusmarques.neopdv.api.order.response.OrderCardResponse;
import com.matheusmarques.neopdv.domain.order.Order;
import com.matheusmarques.neopdv.domain.order.OrderItem;
import com.matheusmarques.neopdv.domain.enums.StatusOrder;
import com.matheusmarques.neopdv.domain.enums.StatusTicket;
import com.matheusmarques.neopdv.api.order.request.OrderStartRequest;
import com.matheusmarques.neopdv.api.order.response.OrderResponse;
import com.matheusmarques.neopdv.api.order.response.OrderStartResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderMap {

    private static String START_ORDER_SUCESS = "A ordem foi iniciada com sucesso, já podem ser lançandos produtos a ela";

    public static Order map(OrderStartRequest request){
        Order order = new Order();
        order.setOwner("");
        order.setCustomer(request.customer());
        order.setTicket(request.ticket());
        order.setAmount(new BigDecimal(0));
        order.setStatus(StatusOrder.OPEN);
        order.setCreatedAt(LocalDateTime.now());
        order.setItemsId(new ArrayList<>());
        return order;
    }

    public static OrderStartResponse toOrderStartResponse(Order order){
        return new OrderStartResponse(
                true,
                START_ORDER_SUCESS,
                order.getId(),
                StatusTicket.FREE,
                order.getCreatedAt()
        );
    }

    public static OrderResponse toOrderResponse(Order order, List<OrderItem> itemList){
        return new OrderResponse(
                order.getId(),
                order.getOwner(),
                order.getCustomer(),
                order.getTicket(),
                order.getAmount(),
                order.getPaymentMethod(),
                order.getStatus(),
                order.getCreatedAt(),
                itemList
        );
    }

    public static List<OrderCardResponse> toOrderCardResponse(List<Order> orders){
        List<OrderCardResponse> responseList = new ArrayList<>();
        for (Order order: orders){
            OrderCardResponse response = new OrderCardResponse(
                    order.getId(),
                    order.getTicket(),
                    order.getCreatedAt(),
                    order.getAmount(),
                    order.getItemsId().size()
            );
            responseList.add(response);
        }
        return responseList;
    }
}
