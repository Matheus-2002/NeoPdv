package com.matheusmarques.neopdv.dto.request;

public record OrderItemRequest(
        String productId,
        int quantity
) {
}
