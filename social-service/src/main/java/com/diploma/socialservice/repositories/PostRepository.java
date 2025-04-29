package com.diploma.socialservice.repositories;

import com.diploma.socialservice.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserIdAndIsDeletedFalse(Long userId);

    List<Post> findByUserIdInAndIsDeletedFalseOrderByCreatedAtDesc(List<Long> userIds, Pageable pageable);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.user.id NOT IN :excludedUserIds " +
            "AND p.isDeleted = false " +
            "ORDER BY p.createdAt DESC")
    List<Post> findRecentPostsNotFromUsers(@Param("excludedUserIds") List<Long> excludedUserIds, Pageable pageable);
}
