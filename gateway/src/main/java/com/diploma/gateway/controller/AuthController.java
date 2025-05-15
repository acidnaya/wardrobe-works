package com.diploma.gateway.controller;

import com.diploma.gateway.dto.AuthRequest;
import com.diploma.gateway.dto.AuthResponse;
import com.diploma.gateway.dto.RegisterRequest;
import com.diploma.gateway.service.AuthService;
import com.diploma.gateway.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {
        passwordResetService.sendResetLink(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token,
                                                @RequestParam String newPassword) {

        if (passwordResetService.resetPassword(token, newPassword)) {
            return ResponseEntity.ok("Password reset successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expired or already used");
    }
}
