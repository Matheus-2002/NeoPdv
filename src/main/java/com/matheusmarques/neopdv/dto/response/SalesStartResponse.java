package com.matheusmarques.neopdv.dto.response;

import com.matheusmarques.neopdv.domain.enumerated.StatusTable;
import java.time.LocalDateTime;

public record SalesStartResponse(
        Boolean success,
        String message,
        String orderId,
        StatusTable statusTable,
        LocalDateTime timestamp
) {
}
