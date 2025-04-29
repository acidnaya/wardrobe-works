package com.diploma.wardrobeservice.transfers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LookbookCreateRequest {
    @Size(max = 20)
    @NotNull
    private String name;

    @Size(max = 200)
    private String description;
}
