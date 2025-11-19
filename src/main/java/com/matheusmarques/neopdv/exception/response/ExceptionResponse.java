package com.matheusmarques.neopdv.exception.response;

import java.time.LocalDateTime;

public record ExceptionResponse (
        Boolean success,
        String message,
        LocalDateTime timestamp
){
}
