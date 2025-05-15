package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.CalendarService;
import com.diploma.wardrobeservice.transfers.CalendarResponse;
import com.diploma.wardrobeservice.transfers.EntryCreateRequest;
import com.diploma.wardrobeservice.transfers.EntryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/{calendarId}/entry/all")
    public ResponseEntity<List<EntryResponse>> getCalendarEntries(@RequestHeader("X-User-ID") Long userId,
                                                                  @RequestParam Long calendarId) {
        var response = calendarService.getEntryByCalendarId(userId, calendarId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/entry/{entryId}")
    public ResponseEntity<EntryResponse> getCalendarEntry(@RequestHeader("X-User-ID") Long userId,
                                                          @RequestParam Long entryId) {
        var response = calendarService.getEntry(userId, entryId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{calendarId}/entry/create")
    public ResponseEntity<Void> createEntry(@RequestHeader("X-User-ID") Long userId,
                                            @RequestParam Long calendarId,
                                            @RequestBody EntryCreateRequest request) {
        calendarService.createEntry(userId, calendarId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/entry/{entryId}")
    public ResponseEntity<Void> deleteEntry(@RequestHeader("X-User-ID") Long userId,
                                            @RequestParam Long entryId) {
        calendarService.deleteEntry(userId, entryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createCalendar(@RequestHeader("X-User-ID") Long userId) {
        calendarService.createCalendar(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CalendarResponse>> getCalendars(@RequestHeader("X-User-ID") Long userId) {
        var response = calendarService.getCalendars(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{otherUserId}/all")
    public ResponseEntity<List<CalendarResponse>> getCalendarsByUser(@RequestHeader("X-User-ID") Long userId,
                                                                     @PathVariable Long otherUserId) {
        var response = calendarService.getCalendarsByUser(otherUserId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{calendarId}/change-privacy")
    public ResponseEntity<Void> changePrivacy(@RequestHeader("X-User-ID") Long userId,
                                              @RequestParam Long calendarId) {
        calendarService.changePrivacy(userId, calendarId);
        return ResponseEntity.ok().build();
    }
}
