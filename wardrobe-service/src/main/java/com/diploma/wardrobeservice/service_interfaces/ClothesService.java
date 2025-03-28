package com.diploma.wardrobeservice.service_interfaces;

import com.diploma.wardrobeservice.entities.Clothes;
import com.diploma.wardrobeservice.transfers.ClothesCreateRequest;
import com.diploma.wardrobeservice.transfers.ClothesFeaturesUpdateRequest;
import com.diploma.wardrobeservice.transfers.ClothesUpdateWardrobeRequest;
import com.diploma.wardrobeservice.transfers.ClothesResponse;
import java.util.List;

public interface ClothesService {
    // Поменять возвращаемое
    Clothes createClothes(String userId, Long wardrobeId, ClothesCreateRequest request);

    List<ClothesResponse> getAllClothesForUser(String userId);

    List<ClothesResponse> getAllClothesForWardrobe(String userId, Long wardrobeId);

    void deleteClothes(String userId, Long clothesId);

    void updateClothesWardrobe(String userId, ClothesUpdateWardrobeRequest request);

    // Поменять возвращаемое
    Clothes updateClothesFeatures(String userId, Long clothesId, ClothesFeaturesUpdateRequest request);

    ClothesResponse getClothesById(String userId, Long clothesId);
}

