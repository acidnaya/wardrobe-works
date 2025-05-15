package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeResponse {
    private Long id;
    private String name;

    public static TypeResponse from(Type type) {
        return new TypeResponse(
                Long.valueOf(type.getId()),
                type.getName());
    }
}
