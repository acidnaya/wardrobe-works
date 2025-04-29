package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Outfit;
import com.diploma.wardrobeservice.entities.OutfitClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutfitClothesRepository extends JpaRepository<OutfitClothes, Long> {
    List<OutfitClothes> findByOutfitIdAndIsDeletedFalse(Long outfitId);
    List<OutfitClothes> findByClothIdAndIsDeletedFalse(Long clothesId);
}
