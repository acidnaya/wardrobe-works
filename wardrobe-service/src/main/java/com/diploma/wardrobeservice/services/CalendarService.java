package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Calendar;
import com.diploma.wardrobeservice.exceptions.ResourceNotFoundException;
import com.diploma.wardrobeservice.repositories.CalendarEntryRepository;
import com.diploma.wardrobeservice.repositories.CalendarRepository;
import com.diploma.wardrobeservice.transfers.CalendarResponse;
import com.diploma.wardrobeservice.transfers.EntryCreateRequest;
import com.diploma.wardrobeservice.transfers.EntryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.diploma.wardrobeservice.entities.CalendarEntry;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CalendarService {
    private final CalendarEntryRepository calendarEntryRepository;
    private final CalendarRepository calendarRepository;
    private final OutfitService outfitService;
    private final AccessService accessService;

    private CalendarEntry getEntryOrThrow(Long entryId) {
        return calendarEntryRepository.findByIdAndIsDeletedFalse(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar entry not found with id: " + entryId));
    }

    private Calendar getCalendarOrThrow(Long calendarId) {
        return calendarRepository.findById(calendarId)
                .orElseThrow(() -> new ResourceNotFoundException("Calendar not found with id: " + calendarId));
    }

    public void createCalendar(Long userId) {
        Calendar calendar = Calendar.builder()
                .userId(userId).build();

        calendarRepository.save(calendar);
    }

    public List<CalendarResponse> getCalendars(Long userId) {
        return calendarRepository.findByUserId(userId).stream()
                .map(CalendarResponse::from)
                .toList();
    }

    public List<CalendarResponse> getCalendarsByUser(Long otherUserId) {
        return calendarRepository.findByUserIdAndIsPrivateFalse(otherUserId).stream()
                .map(CalendarResponse::from)
                .toList();
    }

    public void changePrivacy(Long userId, Long calendarId) {
        var calendar = getCalendarOrThrow(calendarId);

        accessService.checkCalendarFullAccessThrow(userId, calendar);

        calendar.setIsPrivate(!calendar.getIsPrivate());
        calendarRepository.save(calendar);
    }

    public void createEntry(Long userId, Long calendarId, EntryCreateRequest request) {
        var outfit = outfitService.getOutfitOrThrow(request.getOutfitId());
        var calendar = getCalendarOrThrow(calendarId);

        accessService.checkFullEditAccessThrow(userId, outfit.getWardrobe());
        accessService.checkCalendarFullAccessThrow(userId, calendar);

        CalendarEntry calendarEntry = CalendarEntry.builder()
                .userId(userId)
                .outfit(outfit)
                .date(request.getDate())
                .eventNote(request.getEventNote())
                .calendarId(calendarId)
                .build();

        calendarEntryRepository.save(calendarEntry);
    }

    public List<EntryResponse> getEntryByCalendarId(Long userId, Long calendarId) {
        var calendar = getCalendarOrThrow(calendarId);

        accessService.checkCalendarViewAccessThrow(userId, calendar);

        return calendarEntryRepository.findByCalendarIdAndIsDeletedFalse(calendarId).stream()
                .map(EntryResponse::from)
                .toList();
    }

    public EntryResponse getEntry(Long userId, Long entryId) {
        var entry = getEntryOrThrow(entryId);
        var calendar = getCalendarOrThrow(entry.getCalendarId());

        accessService.checkCalendarViewAccessThrow(userId, calendar);

        return EntryResponse.from(entry);
    }

    public void deleteEntry(Long userId, Long entryId) {
        var entry = getEntryOrThrow(entryId);
        var calendar = getCalendarOrThrow(entry.getCalendarId());

        if (!userId.equals(entry.getUserId())) {
            accessService.checkCalendarFullAccessThrow(userId, calendar);
        }

        entry.setIsDeleted(true);
        calendarEntryRepository.save(entry);
    }
}
