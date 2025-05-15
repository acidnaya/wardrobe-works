package com.diploma.wardrobeservice.transfers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsResponse {
    Integer allOutfitsNumber;
    Integer allClothesNumber;
    BrandResponse favouriteBrand;
    ColourResponse favouriteColour;
}
