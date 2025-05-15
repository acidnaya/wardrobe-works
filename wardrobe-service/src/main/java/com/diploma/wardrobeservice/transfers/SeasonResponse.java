package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Season;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeasonResponse {
    private Long id;
    private String name;

    public static SeasonResponse from(Season season) {
        return new SeasonResponse(
                Long.valueOf(season.getId()),
                season.getName());
    }
}
