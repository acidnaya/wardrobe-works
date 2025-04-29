package com.diploma.socialservice.controllers;

import com.diploma.socialservice.services.CommentService;
import com.diploma.socialservice.transfers.CommentCreateRequest;
import com.diploma.socialservice.transfers.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/social-service/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @PostMapping("/{postId}/add")
    public ResponseEntity<Void> addComment(@RequestHeader("X-User-ID") Long userId,
                                           @PathVariable Long postId,
                                           @RequestBody CommentCreateRequest request) {
        commentService.createComment(userId, postId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@RequestHeader("X-User-ID") Long userId,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(userId, commentId);
        return ResponseEntity.ok().build();
    }
}
