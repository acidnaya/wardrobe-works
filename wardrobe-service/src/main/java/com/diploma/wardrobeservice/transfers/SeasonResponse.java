package com.diploma.wardrobeservice.transfers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeasonResponse {
    private Short id;

    private String name;
}
