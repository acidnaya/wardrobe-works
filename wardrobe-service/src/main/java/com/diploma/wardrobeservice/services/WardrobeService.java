package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Wardrobe;
import com.diploma.wardrobeservice.entities.AccessPermission;
import com.diploma.wardrobeservice.exceptions.ResourceNotFoundException;
import com.diploma.wardrobeservice.repositories.*;
import com.diploma.wardrobeservice.transfers.WardrobeCreateRequest;
import com.diploma.wardrobeservice.transfers.WardrobeResponse;
import com.diploma.wardrobeservice.transfers.WardrobeUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class WardrobeService {

    private final AccessService accessService;
    private final WardrobeRepository wardrobeRepository;
    private final AccessRepository accessRepository;
    public final ClothesRepository clothesRepository;
    public final LookbookRepository lookbookRepository;
    public final OutfitRepository outfitRepository;

    public Wardrobe createWardrobe(Long userId, WardrobeCreateRequest request) {
        Wardrobe wardrobe = Wardrobe.builder()
                .creatorId(userId)
                .createdAt(OffsetDateTime.now())
                .name(request.getName())
                .description(request.getDescription())
                .isPrivate(request.getIsPrivate())
                .build();

        return wardrobeRepository.save(wardrobe);
    }

    public Wardrobe getWardrobeOrThrow(Long wardrobeId) {
        return wardrobeRepository.findWardrobeByIdAndIsDeletedFalse(wardrobeId)
                .orElseThrow(() -> new ResourceNotFoundException("Wardrobe not found with id: " + wardrobeId));
    }

    public List<WardrobeResponse> getAllWardrobes(Long userId) {
        List<Wardrobe> ownWardrobes = wardrobeRepository
                .findWardrobeByCreatorIdAndIsDeletedFalse(userId);

        List<Wardrobe> sharedWardrobes = accessRepository.findByGrantedToUserId(userId).stream()
                .map(AccessPermission::getWardrobe)
                .filter(wardrobe -> !wardrobe.getIsDeleted())
                .toList();

        return Stream.concat(ownWardrobes.stream(), sharedWardrobes.stream())
                .distinct()
                .map(WardrobeResponse::from)
                .toList();
    }

    public List<WardrobeResponse> getUserWardrobes(Long userId, Long otherUserId) {
        return wardrobeRepository.findWardrobeByCreatorIdAndIsDeletedFalse(otherUserId).stream()
                .filter(wardrobe -> accessService.checkViewAccess(userId, wardrobe))
                .map(WardrobeResponse::from)
                .toList();
    }

    public WardrobeResponse getWardrobe(Long userId, Long wardrobeId) {
        var wardrobe = getWardrobeOrThrow(wardrobeId);
        accessService.checkViewAccessThrow(userId, wardrobe);
        return WardrobeResponse.from(wardrobe);
    }

    @Transactional
    public void removeWardrobe(Long userId, Long wardrobeId) {
        var wardrobe = getWardrobeOrThrow(wardrobeId);
        accessService.checkFullEditAccessThrow(userId, wardrobe);
        wardrobe.setIsDeleted(true);
        wardrobeRepository.save(wardrobe);

        var clothes = clothesRepository.findByWardrobeId(wardrobeId);
        clothes.forEach(cloth -> cloth.setIsDeleted(true));
        clothesRepository.saveAll(clothes);

        var lookbooks = lookbookRepository.findByWardrobeIdAndIsDeletedFalse(wardrobeId);
        lookbooks.forEach(lb -> lb.setIsDeleted(true));
        lookbookRepository.saveAll(lookbooks);

        var outfits = outfitRepository.findByWardrobeIdAndIsDeletedFalse(wardrobeId);
        outfits.forEach(outfit -> outfit.setIsDeleted(true));
        outfitRepository.saveAll(outfits);
    }

    public void updateWardrobe(Long userId, Long wardrobeId, WardrobeUpdateRequest request) {
        var wardrobe = getWardrobeOrThrow(wardrobeId);
        accessService.checkFullEditAccessThrow(userId, wardrobe);
        if (request.getName() != null) {
            wardrobe.setName(request.getName());
        }
        if (request.getDescription() != null) {
            wardrobe.setDescription(request.getDescription());
        }

        wardrobeRepository.save(wardrobe);
    }

    public void changePrivacy(Long userId, Long wardrobeId) {
        var wardrobe = getWardrobeOrThrow(wardrobeId);
        accessService.checkFullEditAccessThrow(userId, wardrobe);
        wardrobe.setIsPrivate(!wardrobe.getIsPrivate());
        wardrobeRepository.save(wardrobe);
    }
}
