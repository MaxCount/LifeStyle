package com.project.lifestyle.controller;

import com.project.lifestyle.dto.AuthenticationResponse;
import com.project.lifestyle.dto.LoginRequest;
import com.project.lifestyle.dto.RefreshTokenRequest;
import com.project.lifestyle.dto.RegisterRequest;
import com.project.lifestyle.model.User;
import com.project.lifestyle.service.AuthService;
//import com.project.lifestyle.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegisterRequest registerRequest){
        User user = authService.registration(registerRequest);
        authService.setUserDefaultRole(user);

        return new ResponseEntity<>("User Registration Successful", OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login (@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.getUser(loginRequest.getUsername()));
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        authService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }
}
