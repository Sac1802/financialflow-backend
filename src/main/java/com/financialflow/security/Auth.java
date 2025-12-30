package com.financialflow.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import java.util.Date;

@Configuration
@PropertySource("classpath:jwt.properties")
public class Auth {

    @Value("${security.jwt.secret-key}")
    private String privateKeyStr;

    @Value("${security.jwt.expiration-time}")
    private long expirationTime;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(privateKeyStr);
        this.verifier = JWT.require(algorithm)
                .withIssuer("financial-flow")
                .build();
    }

    public String generateToken(int id) {
        return JWT.create()
                .withIssuer("financial-flow")
                .withClaim("userId", id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(algorithm);
    }

    public int verifyToken(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("userId").asInt();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado");
        }
    }

}
