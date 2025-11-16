package com.matheusmarques.neopdv.exception.custom;

public class ValidationTableException extends RuntimeException {
    public ValidationTableException() {
        super("A mesa já está ocupada, escolha outra.");
    }
}
