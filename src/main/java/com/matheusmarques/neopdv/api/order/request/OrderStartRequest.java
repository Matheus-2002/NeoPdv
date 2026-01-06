package com.matheusmarques.neopdv.api.order.request;

import jakarta.validation.constraints.NotBlank;

public record OrderStartRequest(
        @NotBlank(message = "teste") String customer,
        int ticket
){}
