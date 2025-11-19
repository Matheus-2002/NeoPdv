package com.matheusmarques.neopdv.api.order.request;

public record SalesStartRequest(
        String owner,
        int tableNumber
){}
