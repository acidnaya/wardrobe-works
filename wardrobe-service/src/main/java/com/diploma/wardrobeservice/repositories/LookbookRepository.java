package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Lookbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookbookRepository extends JpaRepository<Lookbook, Long> {
    List<Lookbook> findLookbookByWardrobe_IdAndIsDeletedFalse(Long wardrobeId);
    Optional<Lookbook> findLookbookById(Long lookbookId);
    List<Lookbook>findLookbookByWardrobe_CreatorIdAndIsDeletedFalse(Long userId);
    Optional<Lookbook> findByIdAndIsDeletedFalse(Long id);

    List<Lookbook> findByWardrobeIdAndIsDeletedFalse(Long wardrobeId);
}
