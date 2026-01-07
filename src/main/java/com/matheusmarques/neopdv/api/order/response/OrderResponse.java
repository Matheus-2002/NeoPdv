package com.matheusmarques.neopdv.api.order.response;

import com.matheusmarques.neopdv.domain.order.OrderItem;
import com.matheusmarques.neopdv.domain.enums.PaymentMethod;
import com.matheusmarques.neopdv.domain.enums.StatusOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        String id,
        String owner,
        String customer,
        int ticket,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        StatusOrder status,
        LocalDateTime createdDate,
        List<OrderItem> itemList

) {
}
