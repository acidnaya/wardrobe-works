package com.diploma.wardrobeservice.transfers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OutfitClothesRequest {
    private Long clothId;
    private Float x;
    private Float y;
    private Short zIndex;
    private Short rotation;
    private Float scale;
}
