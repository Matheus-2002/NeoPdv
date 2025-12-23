package com.matheusmarques.neopdv.api.order.response;

import com.matheusmarques.neopdv.domain.enums.StatusOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderCardResponse(
        int orderNumber,
        String customerName,
        LocalDateTime createdDate,
        StatusOrder status,
        BigDecimal amount

) {
}
