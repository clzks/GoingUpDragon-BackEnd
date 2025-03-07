package com.goingupdragon.going_up_dragon.util;

import com.goingupdragon.going_up_dragon.entity.UserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // HS256에 안전한 비밀 키 생성
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // ⏳ 1시간 (밀리초 단위)

    // 🔹 JWT 토큰 생성
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(secretKey) // ✅ 키를 사용하여 서명
                .compact();
    }


    // 🔹 JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey) // ✅ 키를 사용하여 검증
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 🔹 토큰에서 사용자 이름(username) 추출
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // ✅ 키를 사용하여 추출
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}