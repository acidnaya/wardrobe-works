package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.ListService;
import com.diploma.wardrobeservice.transfers.BrandResponse;
import com.diploma.wardrobeservice.transfers.ColourResponse;
import com.diploma.wardrobeservice.transfers.SeasonResponse;
import com.diploma.wardrobeservice.transfers.TypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/content")
public class ContentController {

    private final ListService listService;

    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        return ResponseEntity.ok(listService.getAllBrands());
    }

    @GetMapping("/colours")
    public ResponseEntity<List<ColourResponse>> getColours() {
        return ResponseEntity.ok(listService.getAllColours());
    }

    @GetMapping("/seasons")
    public ResponseEntity<List<SeasonResponse>> getSeasons() {
        return ResponseEntity.ok(listService.getAllSeasons());
    }

    @GetMapping("/types")
    public ResponseEntity<List<TypeResponse>> getTypes() {
        return ResponseEntity.ok(listService.getAllTypes());
    }

}
