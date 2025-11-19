package com.matheusmarques.neopdv.api.order.request;

public record OrderItemRequest(
        String productId,
        int quantity
) {
}
