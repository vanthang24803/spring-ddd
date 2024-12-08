package com.api.security;


import lombok.Data;

@Data
public class SecurityUtils {
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    public static final String CLAIMS_NAMESPACE = "https://www.codecake.fr/roles";

}
