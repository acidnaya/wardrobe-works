package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.OutfitClothes;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutfitClothesResponse {
    private String imagePath;
    private Float x;
    private Float y;
    private Short zIndex;
    private Short rotation;
    private Float scale;

    public OutfitClothesResponse from(OutfitClothes outfitClothes, String imagePath) {
        return new OutfitClothesResponse(
                imagePath,
                outfitClothes.getX(),
                outfitClothes.getY(),
                outfitClothes.getZIndex(),
                outfitClothes.getRotation(),
                outfitClothes.getScale()
                );
    }
}
