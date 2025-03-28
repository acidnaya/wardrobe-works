package com.wardrobe.keycloak;

import com.wardrobe.keycloak.dto.AuthErrorResponse;
import com.wardrobe.keycloak.dto.AuthResponse;
import com.wardrobe.keycloak.dto.RegisterResponse;
import com.wardrobe.keycloak.exception.CustomKeycloakException;
import com.wardrobe.keycloak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {

        try {
            RegisterResponse response = userService.registerUser(username, email, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CustomKeycloakException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            String token = userService.getAccessToken(username, password);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value()));
        }
    }

}
