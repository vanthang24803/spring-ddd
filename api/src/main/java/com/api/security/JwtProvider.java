package com.api.security;

import com.app.services.authentication.dto.PayloadResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final String SECRET_KEY = "SECRET_KEY";

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateAccessToken(PayloadResponse account) {
        Date currentDate = new Date();
        long AC_EXP = 60L * 60;
        Date expireDate = new Date(currentDate.getTime() + AC_EXP * 1000);

        return generateToken(account, currentDate, expireDate);
    }

    public String generateRefreshToken(PayloadResponse account) {
        Date currentDate = new Date();
        long RF_EXP = 60L * 60L * 24L * 30L;
        Date expireDate = new Date(currentDate.getTime() + RF_EXP * 1000);

        return generateToken(account, currentDate, expireDate);
    }


    public String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect",
                    ex.fillInStackTrace());
        }
    }


    public Claims decodeToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(PayloadResponse account, Date currentDate, Date expireDate) {

        return Jwts.builder()
                .setSubject(account.getEmail())
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .claim("roles", account.getRoles())
                .compact();
    }


}
