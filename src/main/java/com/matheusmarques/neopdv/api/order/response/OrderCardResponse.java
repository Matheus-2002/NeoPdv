package com.matheusmarques.neopdv.api.order.response;

import com.matheusmarques.neopdv.domain.enums.StatusOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderCardResponse(
        int ticket,
        String customer,
        LocalDateTime createdDate,
        StatusOrder status,
        BigDecimal amount
) {
}
