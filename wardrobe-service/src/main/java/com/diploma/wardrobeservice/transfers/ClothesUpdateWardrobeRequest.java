package com.diploma.wardrobeservice.transfers;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClothesUpdateWardrobeRequest {
    @NotNull
    private Long wardrobeId;
}
