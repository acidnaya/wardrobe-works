package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalendarResponse {
    private Long id;
    private Long userId;
    private Boolean isPrivate;

    public static CalendarResponse from(Calendar calendar) {
        return new CalendarResponse(
                calendar.getId(),
                calendar.getUserId(),
                calendar.getIsPrivate()
        );
    }
}
