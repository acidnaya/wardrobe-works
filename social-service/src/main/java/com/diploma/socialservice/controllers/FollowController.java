package com.diploma.socialservice.controllers;

import com.diploma.socialservice.services.FollowService;
import com.diploma.socialservice.transfers.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/social-service/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followedId}")
    public ResponseEntity<String> follow(@RequestHeader("X-User-ID") Long userId,
                                         @PathVariable Long followedId) {
        followService.follow(userId, followedId);
        return ResponseEntity.ok("Followed successfully");
    }

    @DeleteMapping("/{followedId}")
    public ResponseEntity<String> unfollow(@RequestHeader("X-User-ID") Long userId,
                                           @PathVariable Long followedId) {
        followService.unfollow(userId, followedId);
        return ResponseEntity.ok("Unfollowed successfully");
    }

    @GetMapping("/is-following/{followedId}")
    public ResponseEntity<Boolean> isFollowing(@RequestHeader("X-User-ID") Long userId,
                                               @PathVariable Long followedId) {
        return ResponseEntity.ok(followService.isFollowing(userId, followedId));
    }

    @GetMapping("/followers")
    public ResponseEntity<List<UserResponse>> getFollowers(@RequestHeader("X-User-ID") Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    @GetMapping("/followings")
    public ResponseEntity<List<UserResponse>> getFollowings(@RequestHeader("X-User-ID") Long userId) {
        return ResponseEntity.ok(followService.getFollowings(userId));
    }
}
