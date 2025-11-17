package com.matheusmarques.neopdv.dto.request;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        BigDecimal value,
        int stock,
        String codebar
) {
}
