package com.api.controllers;

import com.app.services.authentication.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @GetMapping()
    public ResponseEntity<?> test() {
        return new ResponseEntity<>(authenticationService.getHelloWorld(), HttpStatus.OK);
    }

}
