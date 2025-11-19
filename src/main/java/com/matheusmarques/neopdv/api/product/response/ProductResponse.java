package com.matheusmarques.neopdv.api.product.response;

import com.matheusmarques.neopdv.domain.product.Product;

public record ProductResponse(
        boolean success,
        String message,
        Product product
) {
}
