package com.matheusmarques.neopdv.api.kafka.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record InvoiceRequest(
        @NotBlank
        String numero,
        @NotBlank
        String cliente,
        @NotNull
        @PositiveOrZero
        Double valor,
        @NotBlank
        String descricao
) {}
