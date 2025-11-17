package com.matheusmarques.neopdv.dto.response;

import com.matheusmarques.neopdv.domain.Product;

public record ProductResponse(
        boolean success,
        String message,
        Product product
) {
}
