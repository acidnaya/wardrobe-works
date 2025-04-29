package com.diploma.socialservice.controllers;

import com.diploma.socialservice.services.PostService;
import com.diploma.socialservice.transfers.PostCreateRequest;
import com.diploma.socialservice.transfers.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/social-service/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@RequestHeader("X-User-ID") Long userId,
                                                @PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(userId, postId));
    }

    @GetMapping("/byUser={otherUserId}/")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@RequestHeader("X-User-ID") Long userId,
                                                             @PathVariable Long otherUserId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId, otherUserId));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestHeader("X-User-ID") Long userId,
                                                   @RequestBody PostCreateRequest request) {
        postService.createPost(userId, request);
        return ResponseEntity.ok("Post was created successfully");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@RequestHeader("X-User-ID") Long userId,
    @PathVariable Long postId) {
        postService.deletePost(userId, postId);
        return ResponseEntity.ok("Post successfully deleted.");
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@RequestHeader("X-User-ID") Long userId,
                                         @PathVariable Long postId) {
        postService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<Void> unlikePost(@RequestHeader("X-User-ID") Long userId,
                                           @PathVariable Long postId) {
        postService.unlikePost(postId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    public List<PostResponse> getFeed(@RequestHeader("X-User-ID") Long userId,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return postService.getFeed(userId, page, size);
    }
}
