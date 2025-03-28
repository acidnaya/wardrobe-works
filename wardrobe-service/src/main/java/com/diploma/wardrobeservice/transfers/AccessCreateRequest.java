package com.diploma.wardrobeservice.transfers;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccessCreateRequest {
    @NotNull
    private Long wardrobeId;

    @NotNull
    private String grantedToUserId;

    @NotNull
    private String accessType;
}
