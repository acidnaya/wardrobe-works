package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.WardrobeService;
import com.diploma.wardrobeservice.transfers.WardrobeCreateRequest;
import com.diploma.wardrobeservice.transfers.WardrobeResponse;
import com.diploma.wardrobeservice.transfers.WardrobeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/wardrobes")
public class WardrobeController {

    private final WardrobeService wardrobeService;

    @PostMapping("/create")
    public ResponseEntity<String> createWardrobe(@RequestHeader("X-User-ID") Long userId,
                                                 @RequestBody WardrobeCreateRequest request) {
        wardrobeService.createWardrobe(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{otherUserId}/all")
    public ResponseEntity<List<WardrobeResponse>> getUserWardrobes(@RequestHeader("X-User-ID") Long userId,
                                                                   @PathVariable Long otherUserId) {
        List<WardrobeResponse> response = wardrobeService.getUserWardrobes(userId, otherUserId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WardrobeResponse>> getWardrobes(@RequestHeader("X-User-ID") Long userId) {
        List<WardrobeResponse> response = wardrobeService.getAllWardrobes(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{wardrobeId}")
    public ResponseEntity<WardrobeResponse> getWardrobe(@RequestHeader("X-User-ID") Long userId,
                                         @PathVariable Long wardrobeId) {
        WardrobeResponse response = wardrobeService.getWardrobe(userId, wardrobeId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{wardrobeId}/update")
    public ResponseEntity<String> updateWardrobe(@RequestHeader("X-User-ID") Long userId,
                                                 @PathVariable Long wardrobeId,
                                                 @RequestBody WardrobeUpdateRequest request) {
        wardrobeService.updateWardrobe(userId, wardrobeId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{wardrobeId}/change-privacy")
    public ResponseEntity<String> changePrivacy(@RequestHeader("X-User-ID") Long userId,
                                                 @PathVariable Long wardrobeId) {
        wardrobeService.changePrivacy(userId, wardrobeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{wardrobeId}/remove")
    public ResponseEntity<String> removeWardrobe(@RequestHeader("X-User-ID") Long userId,
                                                 @PathVariable Long wardrobeId) {
        wardrobeService.removeWardrobe(userId, wardrobeId);
        return ResponseEntity.ok().build();
    }
}
