package com.diploma.socialservice.controllers;

import com.diploma.socialservice.services.UserService;
import com.diploma.socialservice.transfers.UserCreateRequest;
import com.diploma.socialservice.transfers.UserResponse;
import com.diploma.socialservice.transfers.UserUpdateAvatarRequest;
import com.diploma.socialservice.transfers.UserUpdateBioRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/social-service/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/self")
    public ResponseEntity<UserResponse> getSelf(@RequestHeader("X-User-ID") Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/username={username}")
    public ResponseEntity<List<UserResponse>> findUser(@PathVariable String username) {
        List<UserResponse> response = userService.findUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestHeader("X-User-ID") Long userId,
                                            @RequestBody UserCreateRequest request) {
        log.info("Create user: userId={}, username={}", userId, request.getUsername());
        userService.createUser(userId, request);
        return ResponseEntity.ok("Successfully");
    }

    @PostMapping("/bio/update")
    public ResponseEntity<String> updateBio(@RequestHeader("X-User-ID") Long userId,
                                                  @RequestBody UserUpdateBioRequest request) {
        userService.updateBio(userId, request);
        return ResponseEntity.ok("Successfully");
    }

    @PostMapping("/avatar/update")
    public ResponseEntity<String> updateAvatar(@RequestHeader("X-User-ID") Long userId,
                                                     @RequestBody UserUpdateAvatarRequest request) {
        userService.updateAvatar(userId, request);
        return ResponseEntity.ok("Successfully");
    }
}
