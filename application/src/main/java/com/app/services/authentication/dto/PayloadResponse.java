package com.app.services.authentication.dto;


import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayloadResponse {
    private UUID id;

    private String email;

    private String firstName;

    private String lastName;

    private String avatar;

    private Boolean verify;

    Set<String> roles = new HashSet<>();
}
