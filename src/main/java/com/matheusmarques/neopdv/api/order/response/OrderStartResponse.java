package com.matheusmarques.neopdv.api.order.response;

import com.matheusmarques.neopdv.domain.enums.StatusTicket;
import java.time.LocalDateTime;

public record OrderStartResponse(
        Boolean success,
        String message,
        String orderId,
        StatusTicket statusTicket,
        LocalDateTime timestamp
) {
}
