package com.diploma.wardrobeservice.transfers;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class WardrobeResponse {
    private Long id;

    private String creatorId;

    private OffsetDateTime createdAt;

    private String name;

    private String description;

    private Boolean isPrivate;
}
