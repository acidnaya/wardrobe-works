package com.diploma.socialservice.services;

import com.diploma.socialservice.entities.Post;
import com.diploma.socialservice.entities.PostImage;
import com.diploma.socialservice.entities.PostsLike;
import com.diploma.socialservice.entities.User;
import com.diploma.socialservice.exception.ResourceNotAccessibleException;
import com.diploma.socialservice.exception.ResourceNotFoundException;
import com.diploma.socialservice.repositories.*;
import com.diploma.socialservice.transfers.PostCreateRequest;
import com.diploma.socialservice.transfers.PostImageRequest;
import com.diploma.socialservice.transfers.PostResponse;
import com.diploma.socialservice.transfers.TextUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;
    private final PostsLikeRepository postsLikeRepository;
    private final FollowerRepository followerRepository;

    public PostResponse getPostById(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        List<PostImage> images = postImageRepository.findByPostIdOrderByPositionAsc(postId);
        Long likes = postsLikeRepository.countByPostId(post.getId());

        var like = isLiked(post.getId(), userId);
        return PostResponse.from(post, images, likes, like);
    }

    public List<PostResponse> getPostsByUserId(Long userId, Long otherUserId) {
        List<Post> posts = postRepository.findByUserIdAndIsDeletedFalse(otherUserId);

        return posts.stream().map(post -> {
            List<PostImage> images = postImageRepository.findByPostIdOrderByPositionAsc(post.getId());
            Long likes = postsLikeRepository.countByPostId(post.getId());
            var like = isLiked(post.getId(), userId);
            return PostResponse.from(post, images, likes, like);
        }).collect(Collectors.toList());
    }

    public void createPost(Long userId, PostCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = Post.builder()
                .text(request.getText())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        for (PostImageRequest imageReq : request.getPostImages()) {
            PostImage image = PostImage.builder()
                    .post(savedPost)
                    .imagePath(imageReq.getImagePath())
                    .position(imageReq.getPosition())
                    .outfitId(imageReq.getOutfitId())
                    .build();

            postImageRepository.save(image);
        }
    }

    public void deletePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found."));
        if (post.getUser().getId().equals(userId)) {
            post.setIsDeleted(true);
            postRepository.save(post);
        } else {
            throw new ResourceNotAccessibleException("Post deletion is not accessible for current user.");
        }
    }

    public boolean isLiked(Long postId, Long userId) {
        return postsLikeRepository.existsByPostIdAndUserId(postId, userId);
    }

    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!isLiked(postId, userId)) {
            PostsLike like = new PostsLike();
            like.setPost(post);
            like.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found")));
            postsLikeRepository.save(like);
        }
    }

    public void unlikePost(Long postId, Long userId) {
        if (postsLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            postsLikeRepository.deleteByPostIdAndUserId(postId, userId);
        }
    }

    public List<PostResponse> getFeed(Long userId, int page, int size) {
        int halfSize = size / 2;
        Pageable pageable = PageRequest.of(page, halfSize);

        List<Long> followedUserIds = followerRepository.findFollowedUserIdsByFollowerId(userId);
        followedUserIds.add(userId);
        List<Post> followedPosts = postRepository.findByUserIdInAndIsDeletedFalseOrderByCreatedAtDesc(followedUserIds, pageable);
        List<Post> recentOtherPosts = postRepository.findRecentPostsNotFromUsers(followedUserIds, pageable);

        List<Post> combined = new ArrayList<>();
        combined.addAll(followedPosts);
        combined.addAll(recentOtherPosts);

        combined.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        return combined.stream().map(post -> {
            List<PostImage> images = postImageRepository.findByPostIdOrderByPositionAsc(post.getId());
            Long likes = postsLikeRepository.countByPostId(post.getId());

            var like = isLiked(post.getId(), userId);
            return PostResponse.from(post, images, likes, like);
        }).toList();
    }

    public void updatePostText(Long userId, Long postId, TextUpdateRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found."));
        if (post.getUser().getId().equals(userId)) {
            post.setText(request.getText());
            postRepository.save(post);
        } else {
            throw new ResourceNotAccessibleException("Post deletion is not accessible for current user.");
        }
    }
}
