package com.diploma.wardrobeservice.transfers;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CalendarEntryResponse {
    private Long id;
    private Long userId;
    private Long outfitId;
    private LocalDate date;
}
