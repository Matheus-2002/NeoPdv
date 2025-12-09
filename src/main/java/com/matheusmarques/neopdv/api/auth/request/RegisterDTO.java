package com.matheusmarques.neopdv.api.auth.request;

public record RegisterDTO(
        String login,
        String password,
        String role
) {
}
