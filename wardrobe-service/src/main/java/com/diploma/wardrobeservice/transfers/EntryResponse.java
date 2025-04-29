package com.diploma.wardrobeservice.transfers;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import com.diploma.wardrobeservice.entities.CalendarEntry;

@AllArgsConstructor
@Data
public class EntryResponse {
    private Long id;
    private Long userId;
    private OutfitResponse outfit;
    private LocalDate date;
    private String eventNote;
    private Long calendarId;

    public static EntryResponse from(CalendarEntry calendarEntry) {
        return new EntryResponse(
                calendarEntry.getId(),
                calendarEntry.getUserId(),
                OutfitResponse.from(calendarEntry.getOutfit()),
                calendarEntry.getDate(),
                calendarEntry.getEventNote(),
                calendarEntry.getCalendarId()
        );
    }
}
