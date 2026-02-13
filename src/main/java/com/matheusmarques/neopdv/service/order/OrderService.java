package com.matheusmarques.neopdv.service.order;

import com.matheusmarques.neopdv.api.order.response.*;
import com.matheusmarques.neopdv.api.order.request.ItemDeleteRequest;
import com.matheusmarques.neopdv.api.order.request.ItemRequest;
import com.matheusmarques.neopdv.api.order.request.OrderStartRequest;

import java.util.List;

public interface OrderService {

    AmountTodayResponse getAmountToday();

    List<OrderCardResponse> getAllOpen();

    OrderStartResponse orderStart(OrderStartRequest request);

    OrderResponse getOrderById(String orderId);

    OrderResponse removeItem(String orderId, ItemDeleteRequest request);

    ItemResponse addItem(ItemRequest request, String orderId);

    SalesTodayResponse getSalesToday();

    QuantityOrdersOpenResponse getQuantityOpenOders();

}
