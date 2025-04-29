package com.diploma.wardrobeservice.transfers;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EntryCreateRequest {
    @NotNull
    private Long outfitId;
    @NotNull
    private LocalDate date;
    private String eventNote;
}
