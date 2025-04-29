package com.diploma.socialservice.services;

import com.diploma.socialservice.entities.Follower;
import com.diploma.socialservice.repositories.FollowerRepository;
import com.diploma.socialservice.repositories.UserRepository;
import com.diploma.socialservice.transfers.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    public void follow(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        boolean alreadyExists = followerRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
        if (alreadyExists) {
            throw new IllegalStateException("Already following");
        }

        Follower follower = Follower.builder()
                .follower(userRepository.getReferenceById(followerId))
                .followed(userRepository.getReferenceById(followedId))
                .build();

        followerRepository.save(follower);
    }

    public void unfollow(Long followerId, Long followedId) {
        boolean exists = followerRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
        if (!exists) {
            throw new IllegalStateException("Not following");
        }

        followerRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
    }

    public boolean isFollowing(Long followerId, Long followedId) {
        return followerRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }

    public List<UserResponse> getFollowers(Long userId) {
        return followerRepository.findByFollowedId(userId)
                .stream().map(f -> UserResponse.from(f.getFollower())).toList();
    }

    public List<UserResponse> getFollowings(Long userId) {
        return followerRepository.findByFollowerId(userId)
                .stream().map(f -> UserResponse.from(f.getFollowed())).toList();
    }
}