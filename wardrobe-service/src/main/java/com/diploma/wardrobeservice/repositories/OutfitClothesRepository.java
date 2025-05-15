package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.OutfitClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutfitClothesRepository extends JpaRepository<OutfitClothes, Long> {
    List<OutfitClothes> findByOutfitId(Long outfitId);
    List<OutfitClothes> findByClothId(Long clothesId);
    void deleteAllByOutfitId(Long outfitId);
    void deleteAllByClothId(Long clothesId);
}
