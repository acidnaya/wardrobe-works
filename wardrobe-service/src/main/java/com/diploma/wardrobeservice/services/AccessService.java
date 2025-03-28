package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.*;
import com.diploma.wardrobeservice.repositories.*;
import com.diploma.wardrobeservice.transfers.AccessCreateRequest;
import com.diploma.wardrobeservice.transfers.AccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccessService {
    private final AccessRepository accessRepository;
    private final ClothesRepository clothesRepository;
    private final WardrobeRepository wardrobeRepository;
    private final LookbookRepository lookbookRepository;
    private final OutfitRepository outfitRepository;

    AccessResponse toDTO(AccessPermission access) {
        AccessResponse response = new AccessResponse();
        response.setId(access.getId());
        response.setAccessType(access.getAccessType());
        response.setGrantedToUserId(access.getGrantedToUserId());
        response.setWardrobeId(access.getWardrobe().getId());
        return response;
    }

    public List<AccessResponse> getAll(String userId) {
        return accessRepository.findByWardrobe_CreatorId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public boolean grant(String userId, AccessCreateRequest request) {
        var wardrobe = wardrobeRepository.findById(request.getWardrobeId());
        if (wardrobe.isPresent() && checkAccessToWardrobe(userId, wardrobe.get())) {
            AccessPermission permission = new AccessPermission();
            permission.setAccessType(request.getAccessType());
            permission.setGrantedToUserId(request.getGrantedToUserId());
            permission.setWardrobe(wardrobe.get());
            accessRepository.save(permission);
            return true;
        }
        return false;
    }

    public boolean revoke(String userId, Long accessId) {
        var optionalAccess = accessRepository.findById(accessId);
        if (optionalAccess.isEmpty()) {
            return false;
        }
        var access = optionalAccess.get();
        var wardrobe = access.getWardrobe();
        if (checkAccessToWardrobe(userId, wardrobe)) {
            access.setIsDeleted(true);
            accessRepository.save(access);
        }
        return true;
    }

    public boolean checkEditAccessToWardrobe(String userId, Wardrobe wardrobe) {
        return accessRepository.findByWardrobeAndGrantedToUserIdAndIsDeletedFalse(wardrobe, userId).isPresent();
    }

    public boolean checkEditAccessToClothes(String userId, Clothes clothes) {
        return accessRepository.findByWardrobeAndGrantedToUserIdAndIsDeletedFalse(clothes.getWardrobe(), userId).isPresent();
    }

    public boolean checkEditAccessToLookbook(String userId, Lookbook lookbook) {
        return accessRepository.findByWardrobeAndGrantedToUserIdAndIsDeletedFalse(lookbook.getWardrobe(), userId).isPresent();
    }

    public boolean checkEditAccessToOutfit(String userId, Outfit outfit) {
        return accessRepository.findByWardrobeAndGrantedToUserIdAndIsDeletedFalse(outfit.getWardrobe(), userId).isPresent();
    }

    public boolean checkAccessToWardrobe(String userId, Long wardrobeId) {
        //
        var wardrobe = wardrobeRepository.findById(wardrobeId);
        return wardrobe.filter(value -> checkAccessToWardrobe(userId, value)).isPresent();
    }

    public boolean checkAccessToWardrobe(String userId, Wardrobe wardrobe) {
        if (wardrobe.getCreatorId().equals(userId)) {
            return true;
        }
        if (!wardrobe.getIsPrivate()) {
            return true;
        }
        return checkEditAccessToWardrobe(userId, wardrobe);
    }

    public boolean checkAccessToClothes(String userId, Long clothesId) {
        //
        var clothes = clothesRepository.findById(clothesId);
        return clothes.filter(value -> checkAccessToClothes(userId, value)).isPresent();
    }

    public boolean checkAccessToClothes(String userId, Clothes clothes) {
        return checkAccessToWardrobe(userId, clothes.getWardrobe());
    }

    public boolean checkAccessToLookbook(String userId, Long lookbookId) {
        //
        var lookbook = lookbookRepository.findById(lookbookId);
        return lookbook.filter(value -> checkAccessToLookbook(userId, value)).isPresent();
    }

    public boolean checkAccessToLookbook(String userId, Lookbook lookbook) {
        return checkAccessToWardrobe(userId, lookbook.getWardrobe());
    }

    public boolean checkAccessToOutfit(String userId, Long outfitId) {
        //
        var outfit = outfitRepository.findById(outfitId);
        return outfit.filter(value -> checkAccessToOutfit(userId, value)).isPresent();
    }

    public boolean checkAccessToOutfit(String userId, Outfit outfit) {
        return checkAccessToWardrobe(userId, outfit.getWardrobe());
    }

}
