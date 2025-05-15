package com.diploma.wardrobeservice.transfers;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class StatisticsPeriodRequest {
    OffsetDateTime startDate;
    OffsetDateTime endDate;
}
