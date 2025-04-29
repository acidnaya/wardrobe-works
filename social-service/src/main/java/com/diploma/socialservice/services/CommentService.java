package com.diploma.socialservice.services;

import com.diploma.socialservice.entities.Comment;
import com.diploma.socialservice.entities.Post;
import com.diploma.socialservice.entities.User;
import com.diploma.socialservice.exception.ResourceNotAccessibleException;
import com.diploma.socialservice.exception.ResourceNotFoundException;
import com.diploma.socialservice.repositories.CommentRepository;
import com.diploma.socialservice.repositories.PostRepository;
import com.diploma.socialservice.repositories.UserRepository;
import com.diploma.socialservice.transfers.CommentCreateRequest;
import com.diploma.socialservice.transfers.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdAndIsDeletedFalse(postId);
        return comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    public Comment createComment(Long userId, Long postId, CommentCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .text(request.getText())
                .build();

        return commentRepository.save(comment);
    }

    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found."));
        if (comment.getUser().getId().equals(userId)) {
            comment.setIsDeleted(true);
            commentRepository.save(comment);
        } else {
            throw new ResourceNotAccessibleException("Comment deletion not accessible for current user.");
        }
    }
}
