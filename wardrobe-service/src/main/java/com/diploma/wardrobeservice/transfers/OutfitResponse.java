package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Outfit;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Data
@Getter
@Setter
public class OutfitResponse {
    private Long id;

    private String name;

    private String description;

    private OffsetDateTime createdAt;

    private String imagePath;

    public OutfitResponse(Outfit outfit) {
        this.id = outfit.getId();
        this.name = outfit.getName();
        this.description = outfit.getDescription();
        this.createdAt = outfit.getCreatedAt();
        this.imagePath = outfit.getImagePath();
    }
}
