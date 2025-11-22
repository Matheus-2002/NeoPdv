package com.matheusmarques.neopdv.api.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank
        String name,
        @NotNull
        @PositiveOrZero
        BigDecimal value,
        @NotNull
        @PositiveOrZero
        int stock,
        @NotBlank
        String codebar
) {
}
