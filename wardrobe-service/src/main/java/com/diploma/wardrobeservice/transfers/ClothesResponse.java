package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Clothes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.NullableUtils;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClothesResponse {
    private Long id;
    private OffsetDateTime createdAt;
    private String description;
    private String imagePath;
    private Float price;
    private Integer numberOfWear;
    private Long wardrobeId;
    private String typeName;
    private String colourName;
    private String seasonName;
    private String brandName;

    public static ClothesResponse from(Clothes clothes) {
        return new ClothesResponse(
                clothes.getId(),
                clothes.getCreatedAt(),
                clothes.getDescription(),
                clothes.getImagePath(),
                clothes.getPrice(),
                clothes.getNumberOfWear(),
                clothes.getWardrobe().getId(),
                clothes.getTypeNameOrNull(),
                clothes.getColourNameOrNull(),
                clothes.getSeasonNameOrNull(),
                clothes.getBrandNameOrNull()
                );
    }
}