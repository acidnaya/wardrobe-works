package com.diploma.wardrobeservice.transfers;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class ClothesResponse {
    private Long id;

    private OffsetDateTime createdAt;

    private String description;

    private Boolean isDeleted;

    private String imagePath;

    private Float price;

    private Integer numberOfWear;

    private Long wardrobeId;

    private String typeName;

    private String colourName;

    private String seasonName;

    private String brandName;
}