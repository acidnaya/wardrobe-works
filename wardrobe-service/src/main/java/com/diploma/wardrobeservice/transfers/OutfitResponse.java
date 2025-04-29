package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Clothes;
import com.diploma.wardrobeservice.entities.Outfit;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OutfitResponse {
    private Long id;
    private String name;
    private String description;
    private OffsetDateTime createdAt;
    private String imagePath;

    public static OutfitResponse from(Outfit outfit) {
        return new OutfitResponse(
                outfit.getId(),
                outfit.getName(),
                outfit.getDescription(),
                outfit.getCreatedAt(),
                outfit.getImagePath()
        );
    }
}
