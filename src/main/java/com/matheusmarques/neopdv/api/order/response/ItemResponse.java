package com.matheusmarques.neopdv.api.order.response;


public record ItemResponse(
        Boolean success,
        String message,
        String orderItemId
) {

}
