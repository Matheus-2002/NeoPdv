package com.matheusmarques.neopdv.dto.response;

import com.matheusmarques.neopdv.domain.OrderItem;
import com.matheusmarques.neopdv.domain.enumerated.PaymentMethod;
import com.matheusmarques.neopdv.domain.enumerated.StatusOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String id,
        String owner,
        int tableNumber,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        StatusOrder status,
        LocalDateTime createdDate,
        List<OrderItem> itemList

) {
}
