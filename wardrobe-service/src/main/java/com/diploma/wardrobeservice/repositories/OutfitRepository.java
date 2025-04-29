package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, Long> {
    Optional<Outfit> findByIdAndIsDeletedFalse(Long outfitId);

    List<Outfit> findByWardrobeIdAndIsDeletedFalse(Long wardrobeId);
}
