package com.diploma.wardrobeservice.transfers;

import lombok.Data;

import java.util.List;

@Data
public class OutfitUpdateRequest {
    private String name;
    private String description;
    private String imagePath;
    private List<OutfitClothesRequest> clothes;
}
