package com.matheusmarques.neopdv.api.order.request;

public record OrderStartRequest(
        String customer,
        int ticket
){}