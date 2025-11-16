package com.matheusmarques.neopdv.dto.response;

import java.time.LocalDateTime;

public record ExceptionResponse (
        Boolean success,
        String message,
        LocalDateTime timestamp
){
}
