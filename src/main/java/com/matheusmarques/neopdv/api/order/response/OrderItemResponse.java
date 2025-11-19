package com.matheusmarques.neopdv.api.order.response;


public record OrderItemResponse (
        Boolean success,
        String message,
        String orderItemId
) {

}
