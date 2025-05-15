package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Wardrobe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WardrobeRepository extends JpaRepository<Wardrobe, Long> {

    Optional<Wardrobe> findWardrobeByIdAndIsDeletedFalse(Long id);
    Optional<Wardrobe> findWardrobeById(Long id);
    List<Wardrobe> findWardrobeByCreatorIdAndIsDeletedFalse(Long userId);

    List<Wardrobe> findByCreatorIdAndIsDeletedFalse(Long otherUserId);
}
