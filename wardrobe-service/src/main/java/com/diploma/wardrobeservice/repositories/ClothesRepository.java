package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    List<Clothes> findByWardrobe_CreatorIdAndIsDeletedFalse(String userId);

    List<Clothes> findByWardrobeId(Long wardrobeId);

    Optional<Clothes> findById(Long id);
}