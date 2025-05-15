package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Lookbook;
import com.diploma.wardrobeservice.entities.LookbooksOutfit;
import com.diploma.wardrobeservice.entities.OutfitClothes;
import com.diploma.wardrobeservice.exceptions.RequestNotProcessedException;
import com.diploma.wardrobeservice.exceptions.ResourceNotFoundException;
import com.diploma.wardrobeservice.repositories.LookbookRepository;
import com.diploma.wardrobeservice.repositories.LookbooksOutfitRepository;
import com.diploma.wardrobeservice.repositories.OutfitRepository;
import com.diploma.wardrobeservice.repositories.WardrobeRepository;
import com.diploma.wardrobeservice.transfers.LookbookCreateRequest;
import com.diploma.wardrobeservice.transfers.LookbookResponse;
import com.diploma.wardrobeservice.transfers.LookbookUpdateRequest;
import com.diploma.wardrobeservice.transfers.OutfitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LookbookService {

    private final WardrobeRepository wardrobeRepository;
    private final LookbookRepository lookbookRepository;
    private final LookbooksOutfitRepository lookbooksOutfitRepository;
    private final OutfitRepository outfitRepository;
    private final WardrobeService wardrobeService;
    private final OutfitService outfitService;
    private final AccessService accessService;

    public Lookbook getLookbookOrThrow(Long lookbookId) {
        return lookbookRepository.findByIdAndIsDeletedFalse(lookbookId)
                .orElseThrow(() -> new ResourceNotFoundException("Lookbook not found with id: " + lookbookId));
    }

    public void createLookbook(Long userId, Long wardrobeId, LookbookCreateRequest request) {
        var wardrobe = wardrobeService.getWardrobeOrThrow(wardrobeId);
        accessService.checkEditAccessThrow(userId, wardrobe);

        Lookbook lookbook = Lookbook.builder()
                .name(request.getName())
                .description(request.getDescription())
                .wardrobe(wardrobe)
                .build();

        lookbookRepository.save(lookbook);
    }

    public void updateLookbook(Long userId, Long lookbookId, LookbookUpdateRequest request) {
        var lookbook = getLookbookOrThrow(lookbookId);

        var wardrobe = lookbook.getWardrobe();
        accessService.checkEditAccessThrow(userId, wardrobe);

        if (request.getName() != null) {
            lookbook.setName(request.getName());
        }
        if (request.getDescription() != null) {
            lookbook.setDescription(request.getDescription());
        }

        lookbookRepository.save(lookbook);
    }

    public void deleteLookbook(Long userId, Long lookbookId) {
        var lookbook = getLookbookOrThrow(lookbookId);

        var wardrobe = lookbook.getWardrobe();
        accessService.checkEditAccessThrow(userId, wardrobe);

        lookbook.setIsDeleted(true);
        lookbookRepository.save(lookbook);

        var lookbookOutfits = lookbooksOutfitRepository.findAllByLookbookId(lookbookId);
        lookbooksOutfitRepository.deleteAll(lookbookOutfits);
    }

    public void addOutfit(Long userId, Long lookbookId, Long outfitId) {
        var lookbook = getLookbookOrThrow(lookbookId);
        var outfit = outfitService.getOutfitOrThrow(outfitId);

        var wardrobe = lookbook.getWardrobe();
        accessService.checkEditAccessThrow(userId, wardrobe);

        if (!Objects.equals(wardrobe.getId(), outfit.getWardrobe().getId())) {
            throw new RequestNotProcessedException("Outfit and lookbook are not in same wardrobe");
        }

        var optionalLookfit = lookbooksOutfitRepository.findByLookbookAndOutfit(lookbook, outfit);
        if (optionalLookfit.isPresent()) {
            throw new RequestNotProcessedException("Outfit is already in lookbook");
        }

        LookbooksOutfit l = LookbooksOutfit.builder()
                .lookbook(lookbook)
                .outfit(outfit)
                .build();

        lookbooksOutfitRepository.save(l);
    }

    public LookbookResponse getLookbookById(Long userId, Long lookbookId) {
        var lookbook = getLookbookOrThrow(lookbookId);

        var wardrobe = lookbook.getWardrobe();
        accessService.checkViewAccessThrow(userId, wardrobe);

        return LookbookResponse.from(lookbook);
    }

    public List<LookbookResponse> getAllLookbooksByWardrobe(Long userId, Long wardrobeId) {
        var wardrobe = wardrobeService.getWardrobeOrThrow(wardrobeId);
        accessService.checkViewAccessThrow(userId, wardrobe);

        return lookbookRepository
                .findLookbookByWardrobe_IdAndIsDeletedFalse(wardrobeId)
                .stream()
                .map(LookbookResponse::from)
                .toList();
    }

    public List<LookbookResponse> getAllLookbooks(Long userId) {
        return wardrobeRepository.findByCreatorIdAndIsDeletedFalse(userId).stream()
                .flatMap(wardrobe -> lookbookRepository
                        .findLookbookByWardrobe_IdAndIsDeletedFalse(wardrobe.getId())
                        .stream())
                .map(LookbookResponse::from)
                .toList();
    }

    public List<LookbookResponse> getAllLookbooksByUser(Long userId, Long otherUserId) {
        return wardrobeRepository.findByCreatorIdAndIsDeletedFalse(otherUserId).stream()
                .filter(wardrobe -> accessService.checkViewAccess(userId, wardrobe))
                .flatMap(wardrobe -> lookbookRepository
                        .findLookbookByWardrobe_IdAndIsDeletedFalse(wardrobe.getId())
                        .stream())
                .map(LookbookResponse::from)
                .toList();
    }

    public List<OutfitResponse> getOutfitsByLookbookId(Long userId, Long lookbookId) {
        var lookbook = getLookbookOrThrow(lookbookId);
        accessService.checkViewAccessThrow(userId, lookbook.getWardrobe());
        return lookbooksOutfitRepository.findAllByLookbookId(lookbookId)
                .stream()
                .map(LookbooksOutfit::getOutfit)
                .map(OutfitResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteOutfit(Long userId, Long lookbookId, Long outfitId) {
        var outfit = outfitService.getOutfitOrThrow(outfitId);
        var lookbook = getLookbookOrThrow(lookbookId);
        accessService.checkEditAccessThrow(userId, outfit.getWardrobe());
        accessService.checkEditAccessThrow(userId, lookbook.getWardrobe());
        var lookfit = lookbooksOutfitRepository
                .findByLookbookAndOutfit(lookbook, outfit)
                .orElseThrow(() -> new ResourceNotFoundException("Outfit in lookbook is not found"));
        lookbooksOutfitRepository.delete(lookfit);
    }
}
