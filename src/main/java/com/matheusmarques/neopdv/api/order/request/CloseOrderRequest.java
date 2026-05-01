package com.matheusmarques.neopdv.api.order.request;

import com.matheusmarques.neopdv.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public record CloseOrderRequest(
        @NotNull
        PaymentMethod paymentMethod
) {
}
