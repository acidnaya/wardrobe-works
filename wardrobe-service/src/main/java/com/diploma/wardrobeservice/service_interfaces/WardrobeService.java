package com.diploma.wardrobeservice.service_interfaces;

import com.diploma.wardrobeservice.entities.Wardrobe;
import com.diploma.wardrobeservice.transfers.WardrobeCreateRequest;
import com.diploma.wardrobeservice.transfers.WardrobeResponse;

import java.util.List;

public interface WardrobeService {

    Wardrobe createWardrobe(String userId, WardrobeCreateRequest request);

    List<WardrobeResponse> getAllWardrobes(String userId);

    List<WardrobeResponse> getUserWardrobes(String userId, String otherUserId);

    WardrobeResponse getWardrobe(String userId, Long wardrobeId);

    boolean removeWardrobe(String userId, Long wardrobeId);
}
