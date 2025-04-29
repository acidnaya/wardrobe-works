package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.LookbookService;
import com.diploma.wardrobeservice.transfers.LookbookCreateRequest;
import com.diploma.wardrobeservice.transfers.LookbookResponse;
import com.diploma.wardrobeservice.transfers.LookbookUpdateRequest;
import com.diploma.wardrobeservice.transfers.OutfitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/lookbooks")
public class LookbookController {
    private final LookbookService lookbookService;

    @PostMapping("/{wardrobeId}/create")
    public ResponseEntity<String> createLookbook(@RequestHeader("X-User-ID") Long userId,
                                                 @PathVariable Long wardrobeId,
                                                 @RequestBody LookbookCreateRequest request) {
        lookbookService.createLookbook(userId, wardrobeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{lookbookId}")
    public ResponseEntity<LookbookResponse> getLookbook(@RequestHeader("X-User-ID") Long userId, @PathVariable Long lookbookId) {
        LookbookResponse lookbook = lookbookService.getLookbookById(userId, lookbookId);
        return ResponseEntity.ok(lookbook);
    }

    @GetMapping("/{lookbookId}/outfits")
    public ResponseEntity<List<OutfitResponse>> getOutfitsByLookbookId(@RequestHeader("X-User-ID") Long userId, @PathVariable Long lookbookId) {
        List<OutfitResponse> lookbook = lookbookService.getOutfitsByLookbookId(userId, lookbookId);
        return ResponseEntity.ok(lookbook);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LookbookResponse>> getAll(@RequestHeader("X-User-ID") Long userId) {
        List<LookbookResponse> lookbooks = lookbookService.getAllLookbooks(userId);
        return ResponseEntity.ok(lookbooks);
    }

    @GetMapping("/user={otherUserId}/all")
    public ResponseEntity<List<LookbookResponse>> getAllLookbooksByUser(@RequestHeader("X-User-ID") Long userId,
                                                                        @RequestParam("otherUserId") Long otherUserId) {
        List<LookbookResponse> lookbooks = lookbookService.getAllLookbooksByUser(userId, otherUserId);
        return ResponseEntity.ok(lookbooks);
    }

    @GetMapping("/wardrobe={wardrobeId}/all")
    public ResponseEntity<List<LookbookResponse>> getAllLookbooksByWardrobe(@RequestHeader("X-User-ID") Long userId,
                                                                            @PathVariable Long wardrobeId) {
        List<LookbookResponse> lookbooks = lookbookService.getAllLookbooksByWardrobe(userId, wardrobeId);
        return ResponseEntity.ok(lookbooks);
    }

    @PostMapping("/{lookbookId}/add-outfit/{outfitId}")
    public ResponseEntity<String> addOutfit(@RequestHeader("X-User-ID") Long userId,
                                            @PathVariable Long lookbookId,
                                            @PathVariable Long outfitId) {
        lookbookService.addOutfit(userId, lookbookId, outfitId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{lookbookId}/update")
    public ResponseEntity<String> updateLookbook(@RequestHeader("X-User-ID") Long userId,
                                            @PathVariable Long lookbookId,
                                            @RequestBody LookbookUpdateRequest request) {
        lookbookService.updateLookbook(userId, lookbookId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{lookbookId}/delete")
    public ResponseEntity<String> deleteLookbook(@RequestHeader("X-User-ID") Long userId,
                                                 @PathVariable Long lookbookId) {
        lookbookService.deleteLookbook(userId, lookbookId);
        return ResponseEntity.ok().build();
    }
}
