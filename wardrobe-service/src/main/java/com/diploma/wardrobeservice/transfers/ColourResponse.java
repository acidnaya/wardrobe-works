package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Colour;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColourResponse {
    private Long id;
    private String name;
    private String colourcode;

    public static ColourResponse from(Colour colour) {
        return new ColourResponse(
                Long.valueOf(colour.getId()),
                colour.getName(),
                colour.getColourcode());
    }
}
