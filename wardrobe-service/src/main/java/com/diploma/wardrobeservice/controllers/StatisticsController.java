package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.StatisticService;
import com.diploma.wardrobeservice.transfers.StatisticsPerPeriodResponse;
import com.diploma.wardrobeservice.transfers.StatisticsPeriodRequest;
import com.diploma.wardrobeservice.transfers.StatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/statistics")
public class StatisticsController {
    private final StatisticService statisticService;

    @PostMapping("/{wardrobeId}/created")
    public ResponseEntity<StatisticsPerPeriodResponse> getCreatedStatisticsPerPeriod(@RequestHeader("X-User-ID") Long userId,
                                                                              @PathVariable Long wardrobeId,
                                                                              @RequestBody StatisticsPeriodRequest request) {
        var response = statisticService.getCreatedStatisticsPerPeriod(userId, wardrobeId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/planned")
    public ResponseEntity<StatisticsPerPeriodResponse> getPlannedStatisticsPerPeriod(
            @RequestBody StatisticsPeriodRequest request,
            @RequestHeader("X-User-Id") Long userId
    ) {
        StatisticsPerPeriodResponse response = statisticService.getPlannedStatistics(
                userId,
                request.getStartDate().toLocalDate(),
                request.getEndDate().toLocalDate()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{wardrobeId}/all")
    public ResponseEntity<StatisticsResponse> getAllStatistics(
            @PathVariable Long wardrobeId,
            @RequestHeader("X-User-Id") Long userId
    ) {
        StatisticsResponse response = statisticService.getStatistics(userId, wardrobeId);
        return ResponseEntity.ok(response);
    }
}
