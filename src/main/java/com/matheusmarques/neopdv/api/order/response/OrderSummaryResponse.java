package com.matheusmarques.neopdv.api.order.response;

import com.matheusmarques.neopdv.domain.enums.StatusOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSummaryResponse(
        String id,
        int ticket,
        StatusOrder status,
        LocalDateTime createdDate,
        BigDecimal amount,
        int itens
) {
}
