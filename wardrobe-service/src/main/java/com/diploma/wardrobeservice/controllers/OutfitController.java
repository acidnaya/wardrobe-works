package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.entities.Outfit;
import com.diploma.wardrobeservice.services.OutfitServiceImpl;
import com.diploma.wardrobeservice.transfers.OutfitCreateRequest;
import com.diploma.wardrobeservice.transfers.OutfitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/outfits")
public class OutfitController {

    private final OutfitServiceImpl outfitService;

    @PostMapping("/create")
    public ResponseEntity<OutfitResponse> createOutfit(@RequestHeader("X-User-ID") String userId,
                                                       @RequestBody OutfitCreateRequest request) {
        Outfit outfit = outfitService.createOutfit(request);

        OutfitResponse response = new OutfitResponse(outfit);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
