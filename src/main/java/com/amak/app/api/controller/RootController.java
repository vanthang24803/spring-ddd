package com.amak.app.api.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> sendBaseResponse() {
        return ResponseEntity.ok(Map.of(
                "msg", "Hello World",
                "desc", "This is root route of API document is /swagger"
        ));
    }

    @GetMapping("/heath")
    public ResponseEntity<?> heathCheck() {
        return ResponseEntity.ok(Map.of("msg" ,"OK"));
    }
}
