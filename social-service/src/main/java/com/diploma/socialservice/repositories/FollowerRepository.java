package com.diploma.socialservice.repositories;

import com.diploma.socialservice.entities.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
//    Optional<Follower> findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    @Query("SELECT f.followed.id FROM Follower f WHERE f.follower.id = :userId")
    List<Long> findFollowedUserIdsByFollowerId(@Param("userId") Long userId);

    List<Follower> findByFollowedId(Long userId);

    List<Follower> findByFollowerId(Long userId);

    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);

    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);
}
