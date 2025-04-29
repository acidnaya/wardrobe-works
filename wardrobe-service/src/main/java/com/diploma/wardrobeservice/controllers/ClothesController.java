package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.ClothesService;
import com.diploma.wardrobeservice.transfers.ClothesCreateRequest;
import com.diploma.wardrobeservice.transfers.ClothesFeaturesUpdateRequest;
import com.diploma.wardrobeservice.transfers.ClothesResponse;
import com.diploma.wardrobeservice.transfers.OutfitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/clothes")
public class ClothesController {
    private final ClothesService clothesService;

    @PostMapping("/{wardrobeId}/create")
    public ResponseEntity<String> createClothes(@RequestHeader("X-User-ID") Long userId,
                                                @PathVariable Long wardrobeId,
                                                @RequestBody ClothesCreateRequest request) {
       clothesService.createClothes(userId, wardrobeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("{clothesId}/copy/{wardrobeId}")
    public ResponseEntity<String> copyClothes(@RequestHeader("X-User-ID") Long userId,
                                                @PathVariable Long clothesId,
                                                @PathVariable Long wardrobeId) {
        clothesService.copyToWardrobe(userId, clothesId, wardrobeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{clothesId}/update-features")
    public ResponseEntity<String> updateClothesFeatures(@RequestHeader("X-User-ID") Long userId,
                                                        @PathVariable Long clothesId,
                                                        @RequestBody ClothesFeaturesUpdateRequest request) {
        clothesService.updateClothesFeatures(userId, clothesId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClothesResponse>> getAllClothes(@RequestHeader("X-User-ID") Long userId) {
        List<ClothesResponse> clothes = clothesService.getAllClothes(userId);
        return ResponseEntity.ok(clothes);
    }

    @GetMapping("/{clothesId}")
    public ResponseEntity<ClothesResponse> getClothesById(@RequestHeader("X-User-ID") Long userId,
                                                                @PathVariable Long clothesId) {
        ClothesResponse clothes = clothesService.getClothesById(userId, clothesId);
        return ResponseEntity.ok(clothes);
    }

    @GetMapping("/{clothesId}/outfits")
    public ResponseEntity<List<OutfitResponse>> getOutfitsByClothesId(@RequestHeader("X-User-ID") Long userId,
                                                                      @PathVariable Long clothesId) {
        List<OutfitResponse> clothes = clothesService.getOutfitsByClothesId(userId, clothesId);
        return ResponseEntity.ok(clothes);
    }

    @GetMapping("/{wardrobeId}/all")
    public ResponseEntity<List<ClothesResponse>>getClothesForWardrobe(@RequestHeader("X-User-ID") Long userId,
                                                                      @PathVariable Long wardrobeId) {
        var response = clothesService.getAllClothesByWardrobe(userId, wardrobeId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{clothesId}")
    public ResponseEntity<Void> deleteClothes(@RequestHeader("X-User-ID") Long userId,
                              @PathVariable Long clothesId) {
        clothesService.deleteClothes(userId, clothesId);
        return ResponseEntity.ok().build();
    }

}
