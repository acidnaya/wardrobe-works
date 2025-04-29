package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.AccessService;
import com.diploma.wardrobeservice.transfers.AccessCreateRequest;
import com.diploma.wardrobeservice.transfers.AccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/access")
public class AccessController {

    private final AccessService accessService;

    @GetMapping("/all")
    public ResponseEntity<List<AccessResponse>> getAll(@RequestHeader("X-User-ID") Long userId) {
        List<AccessResponse> response = accessService.getAll(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/grant")
    public ResponseEntity<Void> grantAccess(@RequestHeader("X-User-ID") Long userId,
                                              @RequestBody AccessCreateRequest request) {
        accessService.grant(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{accessId}/revoke")
    public ResponseEntity<Void> grantAccess(@RequestHeader("X-User-ID") Long userId,
                                              @PathVariable Long accessId) {
        accessService.revoke(userId, accessId);
        return ResponseEntity.ok().build();
    }
}
