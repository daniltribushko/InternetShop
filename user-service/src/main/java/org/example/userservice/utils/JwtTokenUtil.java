package org.example.userservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.userservice.exceptions.token.TokenNotValidException;
import org.example.userservice.models.entities.Role;
import org.example.userservice.models.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

/**
 * @author Tribushko Danil
 * @since 28.05.2024
 * <p>
 * Класс для работы с jwt токеном
 */
@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Генерация jwt токена
     *
     * @param user сущность пользователя
     * @return jwt токен
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles()
                .stream()
                .map(Role::getName)
                .toList());
        Date issueDate = new Date();
        Date expirationDate = new Date(issueDate.getTime() + 1000 * 60 * 60 * 24);

        return Jwts.builder()
                .claims(claims)
                .signWith(getSecretKey())
                .subject(user.getEmail())
                .issuedAt(issueDate)
                .expiration(expirationDate)
                .compact();
    }

    /**
     * Получение email из токена
     *
     * @param token токен
     * @return email пользователя
     */
    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Проверка токена на валидность
     *
     * @param token токен пользователя
     * @param email адрес пользователя
     */
    public boolean isTokenValid(String token, String email) {
        Claims claims = getClaimsFromToken(token);
        return Objects.equals(claims.getSubject(), email) && new Date().before(claims.getExpiration());
    }

    /**
     * Проверка подписи токена
     *
     * @param token токен пользователя
     */
    public boolean signatureVerification(String token) {
        boolean result;
        try {
            Jwts.parser().verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            result = true;
        } catch (JwtException e) {
            result = false;
        }
        return result;
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new TokenNotValidException();
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
