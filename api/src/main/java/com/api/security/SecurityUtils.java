package com.api.security;

import com.domain.models.RoleEntity;
import com.domain.models.UserEntity;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class SecurityUtils {
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    public static final String CLAIMS_NAMESPACE = "https://www.codecake.fr/roles";


    public static UserEntity mapOauth2AttributesToUser(Map<String, Object> attributes) {
        UserEntity user = new UserEntity();

        String sub = String.valueOf(attributes.get("sub"));

        String username = Optional.ofNullable(attributes.get("preferred_username"))
                .map(Object::toString)
                .map(String::toLowerCase)
                .orElse(null);

        user.setFirstName(Optional.ofNullable(attributes.get("given_name"))
                .or(() -> Optional.ofNullable(attributes.get("nickname")))
                .map(Object::toString)
                .orElse(null));

        user.setLastName(Optional.ofNullable(attributes.get("family_name"))
                .map(Object::toString)
                .orElse(null));

        Optional.ofNullable(attributes.get("gender"))
                .map(Object::toString)
                .ifPresent(gender -> user.setGender("female".equalsIgnoreCase(gender)));

        user.setEmail(Optional.ofNullable(attributes.get("email"))
                .map(Object::toString)
                .orElseGet(() -> {
                    if (sub.contains("|") && username != null && username.contains("@")) {
                        return username;
                    }
                    return sub;
                }));

        user.setAvatar(Optional.ofNullable(attributes.get("picture"))
                .map(Object::toString)
                .orElse(null));

        Optional.ofNullable(attributes.get(CLAIMS_NAMESPACE))
                .map(obj -> (List<String>) obj)
                .ifPresent(authoritiesRaw -> {
                    Set<RoleEntity> authorities = authoritiesRaw.stream()
                            .map(authority -> {
                                RoleEntity auth = new RoleEntity();
                                auth.setName(authority);
                                return auth;
                            })
                            .collect(Collectors.toSet());
                    user.setRoles(authorities);
                });

        return user;
    }

    public static List<SimpleGrantedAuthority> extractAuthorityFromClaims(Map<String, Object> claims) {
        return mapRolesToGrantedAuthorities(getRolesFromClaims(claims));
    }

    private static Collection<String> getRolesFromClaims(Map<String, Object> claims) {
        return (List<String>) claims.get(CLAIMS_NAMESPACE);
    }

    private static List<SimpleGrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles) {
        return roles.stream().filter(role -> role.startsWith("ROLE_")).map(SimpleGrantedAuthority::new).toList();
    }

    public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && getAuthorities(authentication)
                .anyMatch(authority -> Arrays.asList(authorities).contains(authority)));
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication
                instanceof JwtAuthenticationToken jwtAuthenticationToken ?
                extractAuthorityFromClaims(jwtAuthenticationToken.getToken().getClaims()) : authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority);
    }
}
