package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Clothes;
import com.diploma.wardrobeservice.entities.OutfitClothes;
import com.diploma.wardrobeservice.repositories.*;
import com.diploma.wardrobeservice.entities.Outfit;
import com.diploma.wardrobeservice.service_interfaces.OutfitService;
import com.diploma.wardrobeservice.transfers.OutfitClothesRequest;
import com.diploma.wardrobeservice.transfers.OutfitCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class OutfitServiceImpl implements OutfitService {
    private final OutfitRepository outfitRepository;
    private final OutfitClothesRepository outfitClothesRepository;
    private final ClothesRepository clothesRepository;
    private final WardrobeRepository wardrobeRepository;

    @Transactional
    public Outfit createOutfit(OutfitCreateRequest request) {
        Outfit outfit = new Outfit();
        outfit.setName(request.getName());
        outfit.setDescription(request.getDescription());
        outfit.setCreatedAt(OffsetDateTime.now());
        outfit.setWardrobe(wardrobeRepository.findWardrobeById(request.getWardrobeId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wardrobe not found")));
        outfit.setImagePath(request.getImagePath());

        outfitRepository.save(outfit);

        for (OutfitClothesRequest outfitClothesRequest : request.getClothes()) {
            Clothes clothes = clothesRepository.findById(outfitClothesRequest.getClothId())
                    .orElseThrow(() -> new RuntimeException("Clothes not found"));

            OutfitClothes outfitClothes = new OutfitClothes();
            outfitClothes.setOutfit(outfit);
            outfitClothes.setCloth(clothes);
            outfitClothes.setX(outfitClothesRequest.getX());
            outfitClothes.setY(outfitClothesRequest.getY());
            outfitClothes.setZIndex(outfitClothesRequest.getZIndex());
            outfitClothes.setRotation(outfitClothesRequest.getRotation());
            outfitClothes.setScale(outfitClothesRequest.getScale());
            outfitClothesRepository.save(outfitClothes);
        }

        return outfit;
    }

    @Override
    public Outfit updateOutfit(OutfitCreateRequest request) {
        return null;
    }


}
