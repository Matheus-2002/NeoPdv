package com.matheusmarques.neopdv.api.product.request;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        BigDecimal value,
        int stock,
        String codebar
) {
}
