package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColourRepository extends JpaRepository<Colour, Long> {
}
