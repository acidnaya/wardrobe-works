package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Clothes;
import com.diploma.wardrobeservice.entities.OutfitClothes;
import com.diploma.wardrobeservice.exceptions.ResourceNotFoundException;
import com.diploma.wardrobeservice.repositories.*;
import com.diploma.wardrobeservice.entities.Outfit;
import com.diploma.wardrobeservice.transfers.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OutfitService {
    private final OutfitRepository outfitRepository;
    private final OutfitClothesRepository outfitClothesRepository;
    private final WardrobeRepository wardrobeRepository;
    private final WardrobeService wardrobeService;
    private final ClothesService clothesService;
    private final AccessService accessService;
    private final ClothesRepository clothesRepository;
    private final LookbooksOutfitRepository lookbooksOutfitRepository;

    public Outfit getOutfitOrThrow(Long outfitId) {
        return outfitRepository.findByIdAndIsDeletedFalse(outfitId)
                .orElseThrow(() -> new ResourceNotFoundException("Outfit not found with id: " + outfitId));
    }

    @Transactional
    public void createOutfit(Long userId, OutfitCreateRequest request) {
        var wardrobe = wardrobeService.getWardrobeOrThrow(request.getWardrobeId());
        accessService.checkEditAccessThrow(userId, wardrobe);

        Outfit outfit = new Outfit();
        outfit.setName(request.getName());
        outfit.setDescription(request.getDescription());
        outfit.setCreatedAt(OffsetDateTime.now());
        outfit.setWardrobe(wardrobe);
        outfit.setImagePath(request.getImagePath());

        outfitRepository.save(outfit);

        for (OutfitClothesRequest outfitClothesRequest : request.getClothes()) {
            Clothes clothes = clothesService.getClothesOrThrow(outfitClothesRequest.getClothId());
            var clothesWardrobe = clothes.getWardrobe();
            if (wardrobe != clothesWardrobe) {
                throw new ResourceNotFoundException("Wardrobe does not contain clothes with id: " + clothes.getId());
            }

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
    }

    @Transactional
    public void updateOutfit(Long userId, Long outfitId, OutfitUpdateRequest request) {
        var outfit = getOutfitOrThrow(outfitId);
        var wardrobe = outfit.getWardrobe();
        accessService.checkEditAccessThrow(userId, wardrobe);

        if (request.getName() != null) {
            outfit.setName(request.getName());
        }
        if (request.getDescription() != null) {
            outfit.setDescription(request.getDescription());
        }
        if (request.getImagePath() != null) {
            outfit.setImagePath(request.getImagePath());
        }
        outfitRepository.save(outfit);

        if (request.getClothes() != null) {
            outfitClothesRepository.deleteAllByOutfitId(outfitId);
            for (OutfitClothesRequest outfitClothesRequest : request.getClothes()) {
                Clothes clothes = clothesService.getClothesOrThrow(outfitClothesRequest.getClothId());
                var clothesWardrobe = clothes.getWardrobe();
                if (wardrobe != clothesWardrobe) {
                    throw new ResourceNotFoundException("Wardrobe does not contain clothes with id: " + clothes.getId());
                }

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
        }
    }

    public List<OutfitResponse> getAllOutfitsByWardrobe(Long userId, Long wardrobeId) {
        var wardrobe = wardrobeService.getWardrobeOrThrow(wardrobeId);
        accessService.checkViewAccessThrow(userId, wardrobe);
        return outfitRepository.findByWardrobeIdAndIsDeletedFalse(wardrobeId)
                .stream()
                .map(OutfitResponse::from)
                .toList();
    }

    public List<OutfitResponse> getAllOutfitsByUser(Long userId, Long otherUserId) {
        return wardrobeRepository.findWardrobeByCreatorIdAndIsDeletedFalse(otherUserId).stream()
                .filter(wardrobe -> accessService.checkViewAccess(userId, wardrobe))
                .flatMap(wardrobe -> outfitRepository.findByWardrobeIdAndIsDeletedFalse(wardrobe.getId()).stream())
                .map(OutfitResponse::from)
                .toList();
    }

    public List<OutfitResponse> getAllOutfits(Long userId) {
        return wardrobeRepository.findWardrobeByCreatorIdAndIsDeletedFalse(userId).stream()
                .flatMap(wardrobe -> outfitRepository.findByWardrobeIdAndIsDeletedFalse(wardrobe.getId()).stream())
                .map(OutfitResponse::from)
                .toList();
    }

    public OutfitResponse getOutfit(Long userId, Long outfitId) {
        var outfit = getOutfitOrThrow(outfitId);
        accessService.checkViewAccessThrow(userId, outfit.getWardrobe());
        return OutfitResponse.from(outfit);
    }

    public OutfitFullResponse getOutfitFull(Long userId, Long outfitId) {
        var outfit = getOutfitOrThrow(outfitId);
        accessService.checkViewAccessThrow(userId, outfit.getWardrobe());
        var outfitClothes = outfitClothesRepository.findByOutfitId(outfitId);
        var ocr = new java.util.ArrayList<OutfitClothesResponse>();
        for (var oc : outfitClothes) {
            ocr.add(OutfitClothesResponse.from(oc, oc.getCloth().getImagePath()));
        }

        return OutfitFullResponse.from(outfit, ocr);
    }

    public List<ClothesResponse> getClothesByOutfitId(Long userId, Long outfitId) {
        var outfit = getOutfitOrThrow(outfitId);
        accessService.checkViewAccessThrow(userId, outfit.getWardrobe());
        return outfitClothesRepository.findByOutfitId(outfitId)
                .stream()
                .map(OutfitClothes::getCloth)
                .map(ClothesResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteOutfit(Long userId, Long outfitId) {
        var outfit = getOutfitOrThrow(outfitId);
        var wardrobe = outfit.getWardrobe();
        accessService.checkEditAccessThrow(userId, wardrobe);
        lookbooksOutfitRepository.deleteAllByOutfit(outfit);
        outfit.setIsDeleted(true);
        outfitRepository.save(outfit);

    }

}
