package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    public List<Calendar> findByUserId(Long userId);

    public Optional<Calendar> findById(Long calendarId);

    public List<Calendar> findByUserIdAndIsPrivateFalse(Long userId);
}
