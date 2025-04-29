package com.diploma.wardrobeservice.transfers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WardrobeCreateRequest {
    @NotNull
    @Size(max = 200)
    private String name;

    private String description;

    private Boolean isPrivate;
}
