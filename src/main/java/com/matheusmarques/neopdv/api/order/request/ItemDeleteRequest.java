package com.matheusmarques.neopdv.api.order.request;

import jakarta.validation.constraints.NotBlank;

public record ItemDeleteRequest(
        @NotBlank
        String itemId
) {
}
