package com.diploma.wardrobeservice.transfers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClothesCreateRequest extends ClothesFeaturesUpdateRequest{
    @NotNull
    private Long wardrobeId;

    @NotNull
    @Size(max = 200)
    private String imagePath;
}
