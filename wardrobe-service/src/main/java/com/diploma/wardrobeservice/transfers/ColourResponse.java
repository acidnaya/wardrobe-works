package com.diploma.wardrobeservice.transfers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColourResponse {
    private Short id;

    private String name;

    private String colourcode;
}
