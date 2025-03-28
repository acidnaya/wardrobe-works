package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, Long> {
}
