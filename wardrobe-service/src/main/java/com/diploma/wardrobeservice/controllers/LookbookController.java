package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.entities.LookbooksOutfit;
import com.diploma.wardrobeservice.services.LookbookServiceImpl;
import com.diploma.wardrobeservice.transfers.LookbookCreateRequest;
import com.diploma.wardrobeservice.transfers.LookbookResponse;
import com.diploma.wardrobeservice.transfers.OutfitLookbookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/lookbooks")
public class LookbookController {
    private final LookbookServiceImpl lookbookService;

    @GetMapping("/{lookbookId}")
    public ResponseEntity<LookbookResponse> getLookbook(@RequestHeader("X-User-ID") String userId, @PathVariable Long lookbookId) {
        LookbookResponse lookbook = lookbookService.getLookbookById(userId, lookbookId);
        return ResponseEntity.ok(lookbook);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LookbookResponse>> getAllLookbooksByUser(@RequestHeader("X-User-ID") String userId) {
        List<LookbookResponse> lookbooks = lookbookService.getAllLookbooksByUser(userId);
        return ResponseEntity.ok(lookbooks);
    }

    @GetMapping("/{wardrobeId}/all")
    public ResponseEntity<List<LookbookResponse>> getAllLookbooksByWardrobe(@RequestHeader("X-User-ID") String userId,
                                                                            @PathVariable Long wardrobeId) {
        List<LookbookResponse> lookbooks = lookbookService.getAllLookbooksByWardrobe(userId, wardrobeId);
        return ResponseEntity.ok(lookbooks);
    }

    @PostMapping("/{wardrobeId}/create")
    public ResponseEntity<String> createLookbook(@RequestHeader("X-User-ID") String userId,
                                                 @PathVariable Long wardrobeId,
                                                 @RequestBody LookbookCreateRequest request) {
        if (lookbookService.createLookbook(userId, wardrobeId, request) != null) {
            return new ResponseEntity<>("Lookbook was created successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Lookbook was not created.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{lookbookId}/add-outfit")
    public ResponseEntity<String> addOutfit(@RequestHeader("X-User-ID") String userId,
                                            @PathVariable Long lookbookId,
                                            @RequestBody OutfitLookbookRequest request) {
        LookbooksOutfit response = lookbookService.addOutfit(userId, lookbookId, request);
        return new ResponseEntity<>("Outfit was added to lookbook successfully.", HttpStatus.CREATED);
    }
}
