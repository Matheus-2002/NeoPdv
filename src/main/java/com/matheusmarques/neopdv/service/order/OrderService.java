package com.matheusmarques.neopdv.service.order;

import com.matheusmarques.neopdv.api.order.response.OrderCardResponse;
import com.matheusmarques.neopdv.api.order.request.DeleteItemRequest;
import com.matheusmarques.neopdv.api.order.request.OrderItemRequest;
import com.matheusmarques.neopdv.api.order.request.SalesStartRequest;
import com.matheusmarques.neopdv.api.order.response.OrderItemResponse;
import com.matheusmarques.neopdv.api.order.response.OrderResponse;
import com.matheusmarques.neopdv.api.order.response.SalesStartResponse;

import java.util.List;

public interface OrderService {

    List<OrderCardResponse> getAll();
    SalesStartResponse orderStart(SalesStartRequest request);

    OrderResponse getOrderById(String orderId);

    OrderResponse removeItem(String orderId, DeleteItemRequest request);

    OrderItemResponse addItem(OrderItemRequest request, String orderId);

}
