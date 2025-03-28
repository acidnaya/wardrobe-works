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
    public ResponseEntity<List<AccessResponse>> getAll(@RequestHeader("X-User-ID") String userId) {
        List<AccessResponse> response = accessService.getAll(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/grant")
    public ResponseEntity<String> grantAccess(@RequestHeader("X-User-ID") String userId,
                                              @RequestBody AccessCreateRequest request) {
        if (accessService.grant(userId, request)) {
            return new ResponseEntity<>("Access was granted successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Access was not granted.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{accessId}/revoke")
    public ResponseEntity<String> grantAccess(@RequestHeader("X-User-ID") String userId,
                                              @PathVariable Long accessId) {
        if (accessService.revoke(userId, accessId)) {
            return new ResponseEntity<>("Access was revoked successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Access was not revoked.", HttpStatus.NOT_FOUND);
        }
    }
}
