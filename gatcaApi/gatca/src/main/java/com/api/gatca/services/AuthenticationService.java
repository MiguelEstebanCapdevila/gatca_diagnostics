package com.api.gatca.services;

import com.api.gatca.models.request.JwtAuthenticationResponse;
import com.api.gatca.models.request.SignUpRequest;
import com.api.gatca.models.request.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
