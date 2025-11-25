package com.matheusmarques.neopdv.exception.custom;

public class ValidateProduct extends RuntimeException {
    public ValidateProduct(String message) {
        super(message);
    }
}
