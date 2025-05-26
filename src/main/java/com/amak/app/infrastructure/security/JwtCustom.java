package com.amak.app.infrastructure.security;

import com.amak.app.domain.models.TokenEntity;
import com.amak.app.infrastructure.jpa.TokenRepository;
import com.amak.app.shared.constants.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ParseException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtCustom implements JwtDecoder {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private final TokenRepository tokenRepository;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            TokenEntity existingToken = tokenRepository.findTokenEntitiesByValue(token)
                    .orElseThrow(() -> new JwtException(Error.TOKEN_INVALID.getMessage()));

            if (!Boolean.TRUE.equals(existingToken.getIsActive())) {
                throw new JwtException(Error.INVALID_TOKEN.getMessage());
            }
        } catch (ParseException e) {
            throw new JwtException(e.getMessage());
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS256");
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        return nimbusJwtDecoder.decode(token);
    }

}
