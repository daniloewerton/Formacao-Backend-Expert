package br.com.ewerton.authserviceapi.controllers;

import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticateRequest request) {
        return ResponseEntity.ok(AuthenticationResponse.builder()
                        .type("Bearer")
                        .token("token")
                .build());
    }
}
