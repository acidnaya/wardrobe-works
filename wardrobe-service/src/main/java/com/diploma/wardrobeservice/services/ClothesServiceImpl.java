package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.*;
import com.diploma.wardrobeservice.repositories.*;
import com.diploma.wardrobeservice.service_interfaces.ClothesService;
import com.diploma.wardrobeservice.transfers.ClothesCreateRequest;
import com.diploma.wardrobeservice.transfers.ClothesFeaturesUpdateRequest;
import com.diploma.wardrobeservice.transfers.ClothesResponse;
import com.diploma.wardrobeservice.transfers.ClothesUpdateWardrobeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;
    private final WardrobeRepository wardrobeRepository;
    private final TypeRepository typeRepository;
    private final ColourRepository colourRepository;
    private final BrandRepository brandRepository;
    private final SeasonRepository seasonRepository;
    private final AccessService accessService;


    private ClothesResponse toDTO(Clothes clothes) {
        ClothesResponse response = new ClothesResponse();
        response.setId(clothes.getId());
        response.setCreatedAt(clothes.getCreatedAt());
        response.setDescription(clothes.getDescription());
        response.setIsDeleted(clothes.getIsDeleted());
        response.setImagePath(clothes.getImagePath());
        response.setPrice(clothes.getPrice());
        response.setNumberOfWear(clothes.getNumberOfWear());
        response.setWardrobeId(clothes.getWardrobe().getId());
        response.setTypeName(clothes.getType() != null ? clothes.getType().getName() : null);
        response.setColourName(clothes.getColour() != null ? clothes.getColour().getName() : null);
        response.setSeasonName(clothes.getSeason() != null ? clothes.getSeason().getName() : null);
        response.setBrandName(clothes.getBrand() != null ? clothes.getBrand().getName() : null);
        return response;
    }

    @Override
    public Clothes createClothes(String userId, Long wardrobeId, ClothesCreateRequest request) {
        Wardrobe wardrobe = wardrobeRepository.findById(request.getWardrobeId())
                .orElseThrow(() -> new IllegalArgumentException("Wardrobe not found"));
        accessService.checkAccessToWardrobe(userId, wardrobe);

        Clothes clothes = Clothes.builder()
                .wardrobe(wardrobe)
                .imagePath(request.getImagePath())
                .price(request.getPrice())
                .type(typeRepository.findById(Long.valueOf(request.getTypeId())).orElse(null))
                .colour(colourRepository.findById(Long.valueOf(request.getColourId())).orElse(null))
                .season(seasonRepository.findById(Long.valueOf((request.getSeasonId()))).orElse(null))
                .brand(brandRepository.findById(Long.valueOf(request.getBrandId())).orElse(null))
                .description(request.getDescription())
                .build();

        return clothesRepository.save(clothes);
    }

    @Override
    @Transactional
    public Clothes updateClothesFeatures(String userId, Long clothesId, ClothesFeaturesUpdateRequest request) {
        Clothes clothes = clothesRepository.findById(clothesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clothes not found"));
        accessService.checkAccessToClothes(userId, clothes);
        if (request.getPrice() != null) {
            clothes.setPrice(request.getPrice());
        }
        if (request.getTypeId() != null) {
            Type type = typeRepository.findById(Long.valueOf(request.getTypeId()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type not found"));
            clothes.setType(type);
        }
        if (request.getColourId() != null) {
            Colour colour = colourRepository.findById(Long.valueOf(request.getColourId()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colour not found"));
            clothes.setColour(colour);
        }
        if (request.getSeasonId() != null) {
            Season season = seasonRepository.findById(Long.valueOf(request.getSeasonId()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Season not found"));
            clothes.setSeason(season);
        }
        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(Long.valueOf(request.getBrandId())) // dont like this approach
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));
            clothes.setBrand(brand);
        }
        if (request.getDescription() != null) {
            clothes.setDescription(request.getDescription());
        }

        return clothesRepository.save(clothes);
    }

    @Override
    public ClothesResponse getClothesById(String userId, Long clothesId) {
        accessService.checkAccessToClothes(userId, clothesId);
        return null;
    }

    @Override
    public List<ClothesResponse> getAllClothesForUser(String userId) {

        List<Clothes> clothesList = clothesRepository.findByWardrobe_CreatorIdAndIsDeletedFalse(userId);
        return clothesList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClothesResponse> getAllClothesForWardrobe(String userId, Long wardrobeId) {
        accessService.checkAccessToWardrobe(userId, wardrobeId);
        return clothesRepository.findByWardrobeId(wardrobeId).stream().map(this::toDTO).toList();
    }

    @Override
    public void deleteClothes(String userId, Long clothesId) {
        Clothes clothes = clothesRepository.findById(clothesId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clothes not found"));
        accessService.checkAccessToClothes(userId, clothes);
        clothes.setIsDeleted(true);
        clothesRepository.save(clothes);
    }

    @Override
    public void updateClothesWardrobe(String userId, ClothesUpdateWardrobeRequest request) {
//        Clothes clothes = clothesRepository.findById(request.getClothesId()).orElseThrow(() -> new IllegalArgumentException("Clothes not found"));
//
//        if (request.getWardrobeId() != null) clothes.setWardrobeId(request.getWardrobeId());
//        if (request.getPrivacyTypeId() != null) clothes.setPrivacyTypeId(request.getPrivacyTypeId());
//        if (request.getImagePath() != null) clothes.setImagePath(request.getImagePath());
//
//        clothesRepository.save(clothes);
    }

}
