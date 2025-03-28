package com.diploma.wardrobeservice.transfers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandResponse {
    Long id;

    String name;
}
