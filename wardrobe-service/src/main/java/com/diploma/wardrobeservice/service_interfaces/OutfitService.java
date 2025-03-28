package com.diploma.wardrobeservice.service_interfaces;

import com.diploma.wardrobeservice.entities.Outfit;
import com.diploma.wardrobeservice.transfers.OutfitCreateRequest;

public interface OutfitService {

    Outfit createOutfit(OutfitCreateRequest request);

    Outfit updateOutfit(OutfitCreateRequest request);
}
