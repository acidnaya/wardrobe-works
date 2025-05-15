package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Lookbook;
import com.diploma.wardrobeservice.entities.LookbooksOutfit;
import com.diploma.wardrobeservice.entities.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookbooksOutfitRepository extends JpaRepository<LookbooksOutfit, Long> {
    List<LookbooksOutfit> findAllByLookbookId(Long lookbookId);

    Optional<LookbooksOutfit> findByLookbookAndOutfit(Lookbook lookbook, Outfit outfit);

    void deleteAllByOutfit(Outfit outfit);
}
