package com.matheusmarques.neopdv.api.order.request;

import jakarta.validation.constraints.NotBlank;

public record SalesStartRequest(
        @NotBlank(message = "teste") String owner,
        int tableNumber
){}
