package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Lookbook;
import com.diploma.wardrobeservice.entities.LookbooksOutfit;
import com.diploma.wardrobeservice.repositories.LookbookRepository;
import com.diploma.wardrobeservice.repositories.LookbooksOutfitRepository;
import com.diploma.wardrobeservice.repositories.OutfitRepository;
import com.diploma.wardrobeservice.repositories.WardrobeRepository;
import com.diploma.wardrobeservice.service_interfaces.LookbookService;
import com.diploma.wardrobeservice.transfers.LookbookCreateRequest;
import com.diploma.wardrobeservice.transfers.LookbookResponse;
import com.diploma.wardrobeservice.transfers.OutfitLookbookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LookbookServiceImpl implements LookbookService {

    private final WardrobeRepository wardrobeRepository;
    private final LookbookRepository lookbookRepository;
    private final LookbooksOutfitRepository lookbooksOutfitRepository;
    private final OutfitRepository outfitRepository;

    private LookbookResponse toDTO(Lookbook lookbook) {
        LookbookResponse response = LookbookResponse.builder()
                .id(lookbook.getId())
                .wardrobeId(lookbook.getWardrobe().getId())
                .createdAt(lookbook.getCreatedAt())
                .name(lookbook.getName())
                .description(lookbook.getDescription())
                .build();

        return response;
    }

    @Override
    public Lookbook createLookbook(String userId, Long wardrobeId, LookbookCreateRequest request) {
        var wardrobe = wardrobeRepository.findById(wardrobeId).orElseThrow(() -> new IllegalArgumentException("Wardrobe not found"));

        Lookbook lookbook = Lookbook.builder()
                .name(request.getName())
                .description(request.getDescription())
                .wardrobe(wardrobe)
                .build();

        lookbookRepository.save(lookbook);
        return lookbook;
    }

    @Override
    public Lookbook updateLookbook(String userId, Long lookbookId) {
        return null;
    }

    @Override
    public void deleteLookbook(String userId, Long lookbookId) {
        lookbookRepository.findById(lookbookId).ifPresent(lookbook -> {
            lookbook.setIsDeleted(true);
            lookbookRepository.save(lookbook);
        });
    }

    @Override
    public LookbooksOutfit addOutfit(String userId, Long lookbookId, OutfitLookbookRequest request) {
        var lookbook = lookbookRepository.findById(request.getLookbookId()).orElseThrow(() -> new IllegalArgumentException("Lookbook not found"));
        var outfit = outfitRepository.findById(request.getOutfitId()).orElseThrow(() -> new IllegalArgumentException("Outfit not found"));
        LookbooksOutfit l = LookbooksOutfit.builder()
                .lookbook(lookbook)
                .outfit(outfit)
                .build();
        lookbooksOutfitRepository.save(l);
        return null;
    }

    @Override
    public LookbookResponse getLookbookById(String userId, Long lookbookId) {
        return lookbookRepository.findById(lookbookId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<LookbookResponse> getAllLookbooksByWardrobe(String userId, Long wardrobeId) {
        return lookbookRepository.findLookbookByWardrobe_IdAndIsDeletedFalse(wardrobeId).stream().map(this::toDTO).toList();
    }

    public List<LookbookResponse> getAllLookbooksByUser(String userId) {
        return lookbookRepository.findLookbookByWardrobe_CreatorIdAndIsDeletedFalse(userId).stream().map(this::toDTO).toList();
    }
}
