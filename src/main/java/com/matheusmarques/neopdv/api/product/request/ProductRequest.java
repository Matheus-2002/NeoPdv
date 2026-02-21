package com.matheusmarques.neopdv.api.product.request;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(
        
        String name,
        String category,
        @PositiveOrZero
        BigDecimal value,
        @PositiveOrZero
        Integer stock,
        String codebar
) {
}
