package com.diploma.socialservice.transfers;

import com.diploma.socialservice.entities.Post;
import com.diploma.socialservice.entities.PostImage;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class PostResponse {
    private Long id;
    private Long user;
    private OffsetDateTime createdAt;
    private String text;
    private List<String> images;
    private Long likes;
    private Boolean isLiked;

    public static PostResponse from(Post post, List<PostImage> images, Long likes, Boolean isLiked) {
        return new PostResponse(
                post.getId(),
                post.getUser().getId(),
                post.getCreatedAt(),
                post.getText(),
                images.stream().map(PostImage::getImagePath).toList(),
                likes,
                isLiked
        );
    }
}
