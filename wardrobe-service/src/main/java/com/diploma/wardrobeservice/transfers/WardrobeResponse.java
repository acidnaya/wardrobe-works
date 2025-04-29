package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Wardrobe;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class WardrobeResponse {
    private Long id;
    private Long creatorId;
    private OffsetDateTime createdAt;
    private String name;
    private String description;
    private Boolean isPrivate;

    public static WardrobeResponse from(Wardrobe wardrobe) {
        return new WardrobeResponse(
                wardrobe.getId(),
                wardrobe.getCreatorId(),
                wardrobe.getCreatedAt(),
                wardrobe.getName(),
                wardrobe.getDescription(),
                wardrobe.getIsPrivate()
        );
    }
}
