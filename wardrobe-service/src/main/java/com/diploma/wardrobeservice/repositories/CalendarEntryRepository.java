package com.diploma.wardrobeservice.repositories;

import com.diploma.wardrobeservice.entities.CalendarEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarEntryRepository extends JpaRepository<CalendarEntry, Long> {
    public List<CalendarEntry> findByCalendarIdAndIsDeletedFalse(Long userId);
    public Optional<CalendarEntry> findByIdAndIsDeletedFalse(Long id);
    List<CalendarEntry> findAllByUserIdAndDateBetweenAndIsDeletedFalse(Long userId, OffsetDateTime start, OffsetDateTime end);

    @Query("SELECT c FROM CalendarEntry c " +
            "WHERE c.createdAt BETWEEN :startDate AND :endDate " +
            "AND c.userId = :userId " +
            "AND c.isDeleted = false")
    List<CalendarEntry> findAllByCreatedAtBetweenAndUserIdAndNotDeleted(
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            @Param("userId") Long userId
    );

    @Query("SELECT c FROM CalendarEntry c " +
            "WHERE c.date BETWEEN :startDate AND :endDate " +
            "AND c.userId = :userId " +
            "AND c.isDeleted = false")
    List<CalendarEntry> findAllByDateBetweenAndUserIdAndNotDeleted(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("userId") Long userId
    );
}
