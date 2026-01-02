package com.ministorytail.gateway.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/auth")
    public ResponseEntity<String> authFallback() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Auth service unavailable");
    }

    @RequestMapping("/profile")
    public ResponseEntity<String> profileFallback() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Profile service unavailable");
    }

    @RequestMapping("/prompts")
    public ResponseEntity<String> promptFallback() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Prompt service unavailable");
    }

    @RequestMapping("/stories")
    public ResponseEntity<String> storyFallback() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Story service unavailable");
    }

    @RequestMapping("/notifications")
    public ResponseEntity<String> notificationFallback() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Notification service unavailable, request accepted");
    }
}
