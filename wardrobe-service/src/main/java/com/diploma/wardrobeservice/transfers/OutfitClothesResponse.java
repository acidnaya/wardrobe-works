package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.OutfitClothes;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutfitClothesResponse {
    private Long cloth_id;
    private String imagePath;
    private Float x;
    private Float y;
    private Short zIndex;
    private Short rotation;
    private Float scale;

    public static OutfitClothesResponse from(OutfitClothes outfitClothes, String imagePath) {
        return new OutfitClothesResponse(
                outfitClothes.getCloth().getId(),
                imagePath,
                outfitClothes.getX(),
                outfitClothes.getY(),
                outfitClothes.getZIndex(),
                outfitClothes.getRotation(),
                outfitClothes.getScale()
                );
    }
}
