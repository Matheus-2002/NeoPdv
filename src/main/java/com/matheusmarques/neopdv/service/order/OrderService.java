package com.matheusmarques.neopdv.service.order;

import com.matheusmarques.neopdv.api.order.response.OrderCardResponse;
import com.matheusmarques.neopdv.api.order.request.OrderItemDeleteRequest;
import com.matheusmarques.neopdv.api.order.request.OrderItemRequest;
import com.matheusmarques.neopdv.api.order.request.OrderStartRequest;
import com.matheusmarques.neopdv.api.order.response.OrderItemResponse;
import com.matheusmarques.neopdv.api.order.response.OrderResponse;
import com.matheusmarques.neopdv.api.order.response.OrderStartResponse;

import java.util.List;

public interface OrderService {

    List<OrderCardResponse> getAll();
    OrderStartResponse orderStart(OrderStartRequest request);

    OrderResponse getOrderById(String orderId);

    OrderResponse removeItem(String orderId, OrderItemDeleteRequest request);

    OrderItemResponse addItem(OrderItemRequest request, String orderId);

}
