package com.diploma.gateway.clients;

import com.diploma.gateway.config.FeignConfig;
import com.diploma.gateway.dto.UserCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "social-service",
        url = "https://social-service-acidnaya.amvera.io",
//        url = "http://localhost:8081",
        configuration = FeignConfig.class)
public interface SocialServiceClient {

    @PostMapping("/api/v1/social-service/users/create")
    void createUser(@RequestHeader("X-User-ID") Long userId,
                    @RequestBody UserCreateRequest request);
}
