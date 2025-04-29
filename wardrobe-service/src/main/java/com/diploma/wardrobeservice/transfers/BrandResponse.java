package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.Brand;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandResponse {
    Long id;
    String name;

    public static BrandResponse from(Brand brand) {
        return new BrandResponse(
                Long.valueOf(brand.getId()),
                brand.getName());
    }
}
