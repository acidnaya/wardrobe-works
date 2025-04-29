package com.diploma.wardrobeservice.transfers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class OutfitCreateRequest {
    private String name;
    private String description;
    private Long wardrobeId;
    private String imagePath;
    private List<OutfitClothesRequest> clothes;
}
