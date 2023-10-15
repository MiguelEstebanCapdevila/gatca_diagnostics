package com.api.gatca.controllers;

import com.api.gatca.models.request.JwtAuthenticationResponse;
import com.api.gatca.models.request.SignUpRequest;
import com.api.gatca.models.request.SigninRequest;
import com.api.gatca.services.AuthenticationService;
import com.api.gatca.services.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = (AuthenticationServiceImpl) authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}