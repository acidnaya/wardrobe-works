package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.LookbooksOutfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookbooksOutfitRepository extends JpaRepository<LookbooksOutfit, Long> {
    List<LookbooksOutfit> findAllByLookbookId(Long lookbookId);
}
