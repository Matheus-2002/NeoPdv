package com.matheusmarques.neopdv.api.order.response;

import com.matheusmarques.neopdv.domain.enums.StatusTable;
import java.time.LocalDateTime;

public record OrderStartResponse(
        Boolean success,
        String message,
        String orderId,
        StatusTable statusTable,
        LocalDateTime timestamp
) {
}
