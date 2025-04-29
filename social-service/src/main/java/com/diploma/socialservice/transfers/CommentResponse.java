package com.diploma.socialservice.transfers;

import com.diploma.socialservice.entities.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private UserResponse user;
    private String text;
    private OffsetDateTime createdAt;

    public static CommentResponse from(Comment comment) {

        return new CommentResponse(
                comment.getId(),
                UserResponse.from(comment.getUser()),
                comment.getText(),
                comment.getCreatedAt()
        );
    }
}
