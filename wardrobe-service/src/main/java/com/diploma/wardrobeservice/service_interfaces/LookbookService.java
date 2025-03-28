package com.diploma.wardrobeservice.service_interfaces;

import com.diploma.wardrobeservice.entities.Lookbook;
import com.diploma.wardrobeservice.entities.LookbooksOutfit;
import com.diploma.wardrobeservice.transfers.LookbookCreateRequest;
import com.diploma.wardrobeservice.transfers.LookbookResponse;
import com.diploma.wardrobeservice.transfers.OutfitLookbookRequest;

import java.util.List;

public interface LookbookService {
    LookbookResponse getLookbookById(String userId, Long lookbookId);

    List<LookbookResponse> getAllLookbooksByWardrobe(String userId, Long wardrobeId);

    List<LookbookResponse> getAllLookbooksByUser(String userId);

    Lookbook createLookbook(String userId, Long wardrobeId, LookbookCreateRequest request);

    Lookbook updateLookbook(String userId, Long lookbookId);

    void deleteLookbook(String userId, Long lookbookId);

    LookbooksOutfit addOutfit(String userId, Long lookbookId, OutfitLookbookRequest request);
}
