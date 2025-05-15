package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Outfit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class OutfitFullResponse{
    private Long id;
    private String name;
    private String description;
    private OffsetDateTime createdAt;
    private String imagePath;
    private List<OutfitClothesResponse> clothes;

    public static OutfitFullResponse from(Outfit outfit, List<OutfitClothesResponse> clothes) {
        return new OutfitFullResponse(
                outfit.getId(),
                outfit.getName(),
                outfit.getDescription(),
                outfit.getCreatedAt(),
                outfit.getImagePath(),
                clothes
        );
    }
}
