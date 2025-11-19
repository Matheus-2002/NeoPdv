package com.matheusmarques.neopdv.dto.request;

public record NotaFiscalRequest(
        String numero,
        String cliente,
        Double valor,
        String descricao
) {}
