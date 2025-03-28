package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.ClothesServiceImpl;
import com.diploma.wardrobeservice.transfers.ClothesCreateRequest;
import com.diploma.wardrobeservice.transfers.ClothesFeaturesUpdateRequest;
import com.diploma.wardrobeservice.transfers.ClothesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/clothes")
public class ClothesController {
    private final ClothesServiceImpl clothesService;

    @PostMapping("/{wardobeId}/create")
    public ResponseEntity<String> createClothes(@RequestHeader("X-User-ID") String userId,
                                                @PathVariable Long wardrobeId,
                                                @RequestBody ClothesCreateRequest request) {
       if (clothesService.createClothes(userId, wardrobeId, request) != null) {
           return new ResponseEntity<>("Clothes was created successfully.", HttpStatus.CREATED);
       } else {
           return new ResponseEntity<>("Clothes was not created.", HttpStatus.NOT_FOUND);
       }
    }

    @PatchMapping("/{clothesId}/update-features")
    public ResponseEntity<String> updateClothesFeatures(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long clothesId,
            @RequestBody ClothesFeaturesUpdateRequest request) {
        if (clothesService.updateClothesFeatures(userId, clothesId, request) != null) {
            return new ResponseEntity<>("Clothes was updated successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Clothes was not updated.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClothesResponse>> getAllClothes(@RequestHeader("X-User-ID") String userId) {
        List<ClothesResponse> clothes = clothesService.getAllClothesForUser(userId);
        return ResponseEntity.ok(clothes);
    }

    @GetMapping("/{clothesId}")
    public ResponseEntity<ClothesResponse> getClothesById(@RequestHeader("X-User-ID") String userId,
                                                                @PathVariable Long clothesId) {
        ClothesResponse clothes = clothesService.getClothesById(userId, clothesId);
        return ResponseEntity.ok(clothes);
    }

}
