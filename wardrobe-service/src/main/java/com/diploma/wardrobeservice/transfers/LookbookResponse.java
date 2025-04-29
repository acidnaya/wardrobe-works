package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Lookbook;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class LookbookResponse {
    private Long id;
    private Long wardrobeId;
    private OffsetDateTime createdAt;
    private String name;
    private String description;

    public static LookbookResponse from(final Lookbook lookbook) {
        return new LookbookResponse(
                lookbook.getId(),
                lookbook.getWardrobe().getId(),
                lookbook.getCreatedAt(),
                lookbook.getName(),
                lookbook.getDescription()
        );
    }
}
