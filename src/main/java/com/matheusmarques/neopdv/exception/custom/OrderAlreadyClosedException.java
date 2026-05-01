package com.matheusmarques.neopdv.exception.custom;

public class OrderAlreadyClosedException extends RuntimeException {
    public OrderAlreadyClosedException(String message) {
        super(message);
    }
}