package com.matheusmarques.neopdv.api.kafka.request;

public record InvoiceRequest(
        String numero,
        String cliente,
        Double valor,
        String descricao
) {}
