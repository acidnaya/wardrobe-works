package com.diploma.gateway.clients;

import com.diploma.gateway.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "wardrobe-service",
        url = "https://wardrobe-service-acidnaya.amvera.io",
        configuration = FeignConfig.class)
public interface WardrobeServiceClient {

    @PostMapping("/api/v1/wardrobe-service/calendar/create")
    void createCalendar(@RequestHeader("X-User-ID") Long userId);
}
