package com.matheusmarques.neopdv.exception;

import com.matheusmarques.neopdv.domain.enums.StatusTable;
import com.matheusmarques.neopdv.exception.response.ExceptionResponse;
import com.matheusmarques.neopdv.api.order.response.SalesStartResponse;
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

    @ExceptionHandler(ValidateCodebarException.class)
    public ResponseEntity<ExceptionResponse> handleProductFindCodebar(ValidateCodebarException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ValidateProduct.class)
    public ResponseEntity<ExceptionResponse> handleProductFindId(ValidateProduct ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ValidateImageException.class)
    public ResponseEntity<ExceptionResponse> handleImageProduct(ValidateImageException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        false,
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

}
