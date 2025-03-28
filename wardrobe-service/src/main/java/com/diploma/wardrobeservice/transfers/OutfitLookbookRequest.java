package com.diploma.wardrobeservice.transfers;

import lombok.Data;
import lombok.NonNull;

@Data
public class OutfitLookbookRequest {
    @NonNull
    private Long outfitId;

    @NonNull
    private Long LookbookId;

}
