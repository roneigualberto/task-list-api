package com.ronei.tasklist.application.rest;

import com.ronei.tasklist.application.rest.request.CredentialRequest;
import com.ronei.tasklist.application.rest.response.TokenResponse;
import com.ronei.tasklist.infra.security.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthenticationService authenticationService;


    @PostMapping("/token")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody @Valid CredentialRequest request) {

        try {
            TokenResponse token = authenticationService.getToken(request);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
