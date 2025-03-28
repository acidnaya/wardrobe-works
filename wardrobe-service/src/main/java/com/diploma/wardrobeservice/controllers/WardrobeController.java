package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.WardrobeServiceImpl;
import com.diploma.wardrobeservice.transfers.WardrobeCreateRequest;
import com.diploma.wardrobeservice.transfers.WardrobeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/wardrobes")
public class WardrobeController {

    private final WardrobeServiceImpl wardrobeService;

    @PostMapping("/create")
    public ResponseEntity<String> createWardrobe(@RequestHeader("X-User-ID") String userId,
                                                 @RequestBody WardrobeCreateRequest request) {
        if (wardrobeService.createWardrobe(userId, request) != null) {
            return new ResponseEntity<>("Wardrobe was created successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Wardrobe was not created.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{otherUserId}/all")
    public ResponseEntity<List<WardrobeResponse>> getUserWardrobes(@RequestHeader("X-User-ID") String userId,
                                                                   @PathVariable String otherUserId) {
        List<WardrobeResponse> response = wardrobeService.getUserWardrobes(userId, otherUserId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<WardrobeResponse>> getWardrobes(@RequestHeader("X-User-ID") String userId) {
        List<WardrobeResponse> response = wardrobeService.getAllWardrobes(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{wardrobeId}")
    public ResponseEntity<?> getWardrobe(@RequestHeader("X-User-ID") String userId,
                                                              @PathVariable Long wardrobeId) {
        WardrobeResponse response = wardrobeService.getWardrobe(userId, wardrobeId);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>("Wardrobe was not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping ("/{wardrobeId}/remove")
    public ResponseEntity<String> removeWardrobe(@RequestHeader("X-User-ID") String userId,
                                                 @PathVariable Long wardrobeId) {
        if (wardrobeService.removeWardrobe(userId, wardrobeId)) {
            return ResponseEntity.ok("Wardrobe was removed");
        }
        return new ResponseEntity<>("Wardrobe was not found.", HttpStatus.NOT_FOUND);
    }

}
