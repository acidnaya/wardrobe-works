package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.*;
import com.diploma.wardrobeservice.exceptions.ResourceNotFoundException;
import com.diploma.wardrobeservice.repositories.*;
import com.diploma.wardrobeservice.transfers.ClothesCreateRequest;
import com.diploma.wardrobeservice.transfers.ClothesFeaturesUpdateRequest;
import com.diploma.wardrobeservice.transfers.ClothesResponse;
import com.diploma.wardrobeservice.transfers.OutfitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final WardrobeRepository wardrobeRepository;
    private final TypeRepository typeRepository;
    private final ColourRepository colourRepository;
    private final BrandRepository brandRepository;
    private final SeasonRepository seasonRepository;
    private final AccessService accessService;
    private final WardrobeService wardrobeService;
    private final ListService listService;
    private final OutfitClothesRepository outfitClothesRepository;

    public Clothes getClothesOrThrow(Long clothesId) {
        return clothesRepository.findByIdAndIsDeletedFalse(clothesId)
                .orElseThrow(() -> new ResourceNotFoundException("Clothes not found with id: " + clothesId));
    }

    public Clothes createClothes(Long userId, Long wardrobeId, ClothesCreateRequest request) {
        var wardrobe = wardrobeService.getWardrobeOrThrow(wardrobeId);
        accessService.checkEditAccessThrow(userId, wardrobe);

        Clothes clothes = Clothes.builder()
                .wardrobe(wardrobe)
                .imagePath(request.getImagePath())
                .price(request.getPrice())
                .type(typeRepository.findById(request.getTypeId()).orElse(null))
                .colour(colourRepository.findById(request.getColourId()).orElse(null))
                .season(seasonRepository.findById(request.getSeasonId()).orElse(null))
                .brand(brandRepository.findById(request.getBrandId()).orElse(null))
                .description(request.getDescription())
                .build();

        return clothesRepository.save(clothes);
    }

    @Transactional
    public Clothes updateClothesFeatures(Long userId, Long clothesId, ClothesFeaturesUpdateRequest request) {
        var clothes = getClothesOrThrow(clothesId);
        var wardrobe = clothes.getWardrobe();
        accessService.checkEditAccessThrow(userId, wardrobe);

        if (request.getPrice() != null) {
            clothes.setPrice(request.getPrice());
        }
        if (request.getTypeId() != null) {
            clothes.setType(listService.getTypeOrThrow(request.getTypeId()));
        }
        if (request.getColourId() != null) {
            clothes.setColour(listService.getColourOrThrow(request.getColourId()));
        }
        if (request.getSeasonId() != null) {
            clothes.setSeason(listService.getSeasonOrThrow(request.getSeasonId()));
        }
        if (request.getBrandId() != null) {
            clothes.setBrand(listService.getBrandOrThrow(request.getBrandId()));
        }
        if (request.getDescription() != null) {
            clothes.setDescription(request.getDescription());
        }

        return clothesRepository.save(clothes);
    }

    public ClothesResponse getClothesById(Long userId, Long clothesId) {
        var clothes = getClothesOrThrow(clothesId);
        var wardrobe = clothes.getWardrobe();
        accessService.checkViewAccessThrow(userId, wardrobe);
        return ClothesResponse.from(clothes);
    }

    public List<ClothesResponse> getAllClothes(Long userId) {

        List<Clothes> clothesList = clothesRepository.findByWardrobe_CreatorIdAndIsDeletedFalse(userId);
        return clothesList.stream()
                .map(ClothesResponse::from)
                .collect(Collectors.toList());
    }

    public List<ClothesResponse> getAllClothesByUser(Long userId, Long otherUserId) {
        return wardrobeRepository.findByCreatorIdAndIsDeletedFalse(otherUserId).stream()
                .filter(wardrobe -> accessService.checkViewAccess(userId, wardrobe))
                .flatMap(wardrobe -> clothesRepository.findByWardrobeIdAndIsDeletedFalse(wardrobe.getId()).stream())
                .map(ClothesResponse::from)
                .toList();
    }

    public List<ClothesResponse> getAllClothesByWardrobe(Long userId, Long wardrobeId) {
        var wardrobe = wardrobeService.getWardrobeOrThrow(wardrobeId);
        accessService.checkViewAccessThrow(userId, wardrobe);
        return clothesRepository.findByWardrobeIdAndIsDeletedFalse(wardrobeId)
                .stream()
                .map(ClothesResponse::from)
                .toList();
    }

    public void deleteClothes(Long userId, Long clothesId) {
        var clothes = getClothesOrThrow(clothesId);
        var wardrobe = clothes.getWardrobe();
        accessService.checkEditAccessThrow(userId, wardrobe);
        clothes.setIsDeleted(true);
        clothesRepository.save(clothes);
    }

    public void copyToWardrobe(Long userId, Long clothesId, Long wardrobeId) {
        var clothes = getClothesOrThrow(clothesId);
        var fromWardrobe = clothes.getWardrobe();
        var toWardrobe = wardrobeService.getWardrobeOrThrow(wardrobeId);
        accessService.checkViewAccessThrow(userId, fromWardrobe);
        accessService.checkEditAccessThrow(userId, toWardrobe);

        Clothes newClothes = Clothes.builder()
                .wardrobe(toWardrobe)
                .imagePath(clothes.getImagePath())
                .price(clothes.getPrice())
                .type(clothes.getType())
                .colour(clothes.getColour())
                .season(clothes.getSeason())
                .brand(clothes.getBrand())
                .description(clothes.getDescription())
                .build();

        clothesRepository.save(newClothes);
    }

    public List<OutfitResponse> getOutfitsByClothesId(Long userId, Long clothesId) {
        var clothes = getClothesOrThrow(clothesId);
        accessService.checkViewAccessThrow(userId, clothes.getWardrobe());
        return outfitClothesRepository.findByClothIdAndIsDeletedFalse(clothesId)
                .stream()
                .map(OutfitClothes::getOutfit)
                .map(OutfitResponse::from)
                .collect(Collectors.toList());
    }

}
