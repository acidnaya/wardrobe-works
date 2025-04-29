package com.diploma.wardrobeservice.transfers;

import lombok.Data;

@Data
public class ClothesFeaturesUpdateRequest {
    private Float price;

    private Long typeId;

    private Long colourId;

    private Long seasonId;

    private Long brandId;

    private String description;
}
