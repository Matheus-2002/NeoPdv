package com.matheusmarques.neopdv.api.order.response;

public record TopProductMonthResponse(
        String productName,
        String productId,
        int quantitySold
) {
}
