package com.diploma.wardrobeservice.transfers;

import lombok.Data;

@Data
public class ClothesFeaturesUpdateRequest {
    private Float price;

    private Short typeId;

    private Short colourId;

    private Short seasonId;

    private Integer brandId;

    private String description;
}
