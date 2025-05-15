package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.OffsetDateTime;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    List<Clothes> findByWardrobe_CreatorIdAndIsDeletedFalse(Long userId);

    List<Clothes> findByWardrobeId(Long wardrobeId);
    List<Clothes> findByWardrobeIdAndIsDeletedFalse(Long wardrobeId);

    Optional<Clothes> findById(Long id);
    Optional<Clothes> findByIdAndIsDeletedFalse(Long id);

    List<Clothes> findAllByWardrobeIdAndCreatedAtBetweenAndIsDeletedFalse(Long wardrobeId, OffsetDateTime start, OffsetDateTime end);
    List<Clothes> findAllByWardrobeIdAndIsDeletedFalse(Long wardrobeId);
}