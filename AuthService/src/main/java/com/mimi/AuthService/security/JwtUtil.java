package com.mimi.AuthService.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "MIMI_SUPER_SECRETO_2025";

    public String generateToken(String email, Long rolId) {

        return Jwts.builder()
                .claim("email", email)
                .claim("rol", rolId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24)) // 24 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
