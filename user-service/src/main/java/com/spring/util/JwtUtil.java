package com.spring.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(
        "mySuperSecureSecretKey1234567890!".getBytes()
    );

    public static String generateToken(String username, String role) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .claim("role", role) // e.g., ROLE_ADMIN or ROLE_USER
            .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000)) // 5 minutes
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public static String getUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    public static Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY) // Ensure this matches your signing key
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

}
