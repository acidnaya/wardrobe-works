package com.diploma.wardrobeservice.transfers;

import lombok.Builder;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Builder
public class LookbookResponse {
    private Long id;

    private Long wardrobeId;

    private OffsetDateTime createdAt;

    private String name;

    private String description;
}
