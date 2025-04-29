package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.AccessPermission;
import com.diploma.wardrobeservice.entities.Wardrobe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccessRepository extends JpaRepository<AccessPermission, Long> {
    List<AccessPermission> findByWardrobe_CreatorId(Long userId);
    Optional<AccessPermission> findByWardrobeAndGrantedToUserId(Wardrobe wardrobe, Long grantedToUserId);
    boolean existsByIdAndGrantedToUserId(Long accessId, Long userId);
    void deleteByIdAndGrantedToUserId(Long accessId, Long userId);
    List<AccessPermission> findByGrantedToUserId(Long userId);
}
