package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.CalendarEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarEntryRepository extends JpaRepository<CalendarEntry, Long> {
    public List<CalendarEntry> findByCalendarIdAndIsDeletedFalse(Long userId);
    public Optional<CalendarEntry> findByIdAndIsDeletedFalse(Long id);
}
