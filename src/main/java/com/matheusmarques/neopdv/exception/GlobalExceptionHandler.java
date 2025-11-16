package com.matheusmarques.neopdv.exception;

import com.matheusmarques.neopdv.domain.enumerated.StatusTable;
import com.matheusmarques.neopdv.dto.response.ExceptionResponse;
import com.matheusmarques.neopdv.dto.response.SalesStartResponse;
import com.matheusmarques.neopdv.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationTableException.class)
    public ResponseEntity<SalesStartResponse> handleValidationTable(ValidationTableException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        new SalesStartResponse(
                                false,
                                ex.getMessage(),
                                null,
                                StatusTable.OCCUPIED,
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleOrderNotFound(OrderNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(StatusOrderException.class)
    public ResponseEntity<ExceptionResponse> handleStatusOrder(StatusOrderException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ExceptionResponse(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFound(ProductNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleItemNotFound(ItemNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }
}
