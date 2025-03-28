package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.OutfitClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutfitClothesRepository extends JpaRepository<OutfitClothes, Long> {
}
