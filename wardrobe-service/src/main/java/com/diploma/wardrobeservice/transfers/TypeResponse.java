package com.diploma.wardrobeservice.transfers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeResponse {
    private Short id;

    private String name;
}
