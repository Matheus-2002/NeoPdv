package com.matheusmarques.neopdv.api.order.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemRequest(
        @NotBlank
        String productId,
        @NotNull
        @PositiveOrZero
        int quantity
) {
}
