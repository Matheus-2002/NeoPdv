package com.matheusmarques.neopdv.api.auth.request;

public record AuthenticationDTO (
        String login,
        String password
){}
