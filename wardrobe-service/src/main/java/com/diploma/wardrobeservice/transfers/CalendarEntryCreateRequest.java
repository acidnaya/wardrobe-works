package com.diploma.wardrobeservice.transfers;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CalendarEntryCreateRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long outfitId;

    @NotNull
    private LocalDate date;
}
