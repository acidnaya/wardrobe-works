package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.OutfitService;
import com.diploma.wardrobeservice.transfers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/outfits")
public class OutfitController {

    private final OutfitService outfitService;

    @GetMapping("/{outfitId}")
    public ResponseEntity<OutfitResponse> getOutfit(@RequestHeader("X-User-ID") Long userId,
                                                       @RequestParam("outfitId") Long outfitId) {
        var response = outfitService.getOutfit(userId, outfitId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{outfitId}/full")
    public ResponseEntity<OutfitFullResponse> getFullOutfit(@RequestHeader("X-User-ID") Long userId,
                                                            @RequestParam("outfitId") Long outfitId) {
        var response = outfitService.getOutfitFull(userId, outfitId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{outfitId}/clothes")
    public ResponseEntity<List<ClothesResponse>> getClothesByOutfitId(@RequestHeader("X-User-ID") Long userId,
                                                                      @RequestParam("outfitId") Long outfitId) {
        var response = outfitService.getClothesByOutfitId(userId, outfitId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OutfitResponse>> getAllOutfits(@RequestHeader("X-User-ID") Long userId) {
        var response = outfitService.getAllOutfits(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user={otherUserId}/all")
    public ResponseEntity<List<OutfitResponse>> getAllOutfitsForUser(@RequestHeader("X-User-ID") Long userId,
                                                                     @PathVariable("otherUserId") Long otherUserId) {
        var response = outfitService.getAllOutfitsByUser(userId, otherUserId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/wardrobe={wardrobeId}/all")
    public ResponseEntity<List<OutfitResponse>> getAllOutfitsForWardrobe(@RequestHeader("X-User-ID") Long userId,
                                                                         @PathVariable("wardrobeId") Long wardrobeId) {
        var response = outfitService.getAllOutfitsByWardrobe(userId, wardrobeId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOutfit(@RequestHeader("X-User-ID") Long userId,
                                                       @RequestBody OutfitCreateRequest request) {
        outfitService.createOutfit(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{outfitId}/update")
    public ResponseEntity<String> updateOutfit(@RequestHeader("X-User-ID") Long userId,
                                               @PathVariable("outfitId") Long outfitId,
                                               @RequestBody OutfitUpdateRequest request) {
        outfitService.updateOutfit(userId, outfitId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{outfitId}")
    public ResponseEntity<String> deleteOutfit(@RequestHeader("X-User-ID") Long userId,
                                               @RequestParam("outfitId") Long outfitId) {
        outfitService.deleteOutfit(userId, outfitId);
        return ResponseEntity.ok().build();
    }
}
