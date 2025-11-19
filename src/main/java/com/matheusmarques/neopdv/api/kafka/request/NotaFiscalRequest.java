package com.matheusmarques.neopdv.api.kafka.request;

public record NotaFiscalRequest(
        String numero,
        String cliente,
        Double valor,
        String descricao
) {}
