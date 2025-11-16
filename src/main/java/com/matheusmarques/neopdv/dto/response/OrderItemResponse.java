package com.matheusmarques.neopdv.dto.response;


public record OrderItemResponse (
        Boolean success,
        String message,
        String orderItemId
) {

}
