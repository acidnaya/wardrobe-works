package com.diploma.wardrobeservice.transfers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsPerPeriodResponse {
    Integer outfitsNumber;
    Integer clothesNumber;
}
