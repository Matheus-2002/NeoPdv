package com.matheusmarques.neopdv.api.order.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderCardResponse(
        String id,
        int ticket,
        LocalDateTime createdDate,
        BigDecimal amount,
        int itens
) {
}
