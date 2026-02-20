package com.matheusmarques.neopdv.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matheusmarques.neopdv.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    private static final long ACCESS_EXPIRATION_MINUTES = 2;
    private static final long REFRESH_EXPIRATION_DAYS = 7;


    public String generateAccessToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDateMinutes(ACCESS_EXPIRATION_MINUTES))
                    .sign(algorithm);

        }catch (JWTCreationException ex){
            throw new RuntimeException("Error while generation token", ex);

        }
    }

    public String generateRefreshToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDateDays(REFRESH_EXPIRATION_DAYS))
                    .sign(algorithm);

        }catch (JWTCreationException ex){
            throw new RuntimeException("Error while generation token", ex);

        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException ex){
            return "";
        }
    }

    private Instant genExpirationDateMinutes(long minutes) {
        return LocalDateTime.now()
                .plusMinutes(minutes)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genExpirationDateDays(long days) {
        return LocalDateTime.now()
                .plusDays(days)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
