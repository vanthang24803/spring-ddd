package com.amak.app.infrastructure.security;

import com.amak.app.domain.enums.TokenName;
import com.amak.app.domain.models.UserEntity;
import com.amak.app.shared.common.JwtPayload;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.refresh.key}")
    private String refreshKey;

    public final Instant SECRET_EXP = Instant.now().plus(7, ChronoUnit.DAYS);
    public final Instant REFRESH_EXP = Instant.now().plus(7, ChronoUnit.DAYS);

    private static final JWSHeader HEADER = new JWSHeader(JWSAlgorithm.HS256);

    public String generateAccessToken(JwtPayload payload) {
        return generateToken(payload, secretKey, 7);
    }

    public String generateRefreshToken(JwtPayload payload) {
        return generateToken(payload, refreshKey, 30);
    }

    private String generateToken(JwtPayload payload, String key, int amountToAdd) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(payload.getUsername())
                    .issuer("amak.com")
                    .issueTime(Date.from(Instant.now()))
                    .expirationTime(Date.from(Instant.now().plus(amountToAdd, ChronoUnit.DAYS)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("roles", payload.getRoles())
                    .build();

            JWSObject jwsObject = new JWSObject(HEADER, new Payload(claimsSet.toJSONObject()));
            jwsObject.sign(new MACSigner(key.getBytes()));
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("Failed to generate JWT", e);
            throw new RuntimeException("JWT creation failed", e);
        }
    }

    public String generateJwtCheckSum(UserEntity user, TokenName tokenName) {
        try {
            String input = user.getId() + "-" + user.getUsername() + "-" + tokenName.name() + "-" + Instant.now().toString();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
