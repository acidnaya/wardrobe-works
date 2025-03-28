package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.Wardrobe;
import com.diploma.wardrobeservice.repositories.WardrobeRepository;
import com.diploma.wardrobeservice.service_interfaces.WardrobeService;
import com.diploma.wardrobeservice.transfers.WardrobeCreateRequest;
import com.diploma.wardrobeservice.transfers.WardrobeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WardrobeServiceImpl implements WardrobeService {

    private final WardrobeRepository wardrobeRepository;
    private final AccessService accessService;

    private WardrobeResponse toDTO(Wardrobe wardrobe){
        WardrobeResponse wardrobeResponse = new WardrobeResponse();
        wardrobeResponse.setId(wardrobe.getId());
        wardrobeResponse.setName(wardrobe.getName());
        wardrobeResponse.setDescription(wardrobe.getDescription());
        wardrobeResponse.setCreatorId(wardrobe.getCreatorId());
        wardrobeResponse.setIsPrivate(wardrobe.getIsPrivate());
        wardrobeResponse.setCreatedAt(wardrobe.getCreatedAt());

        return wardrobeResponse;
    }


    @Override
    public Wardrobe createWardrobe(String userId, WardrobeCreateRequest request) {

        Wardrobe wardrobe = Wardrobe.builder()
                .creatorId(userId)
                .createdAt(OffsetDateTime.now())
                .name(request.getName())
                .description(request.getDescription())
                .isPrivate(request.getIsPrivate())
                .build();

        return wardrobeRepository.save(wardrobe);
    }

    //Добавить сюда те гардеробы к которым дали доступ этому пользователю
    @Override
    public List<WardrobeResponse> getAllWardrobes(String userId) {
        return wardrobeRepository.findWardrobeByCreatorIdAndIsDeletedFalse(userId).stream().map(this::toDTO).toList();
    }

    @Override
    public List<WardrobeResponse> getUserWardrobes(String userId, String otherUserId) {
        return wardrobeRepository.findWardrobeByCreatorIdAndIsDeletedFalse(otherUserId).stream()
                .filter(wardrobe -> accessService.checkAccessToWardrobe(userId, wardrobe))
                .map(this::toDTO)
                .toList();
    }

    @Override
    public WardrobeResponse getWardrobe(String userId, Long wardrobeId) {
        if (!accessService.checkAccessToWardrobe(userId, wardrobeId)) {
            return null;
        }
        return wardrobeRepository.findWardrobeByIdAndIsDeletedFalse(wardrobeId)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public boolean removeWardrobe(String userId, Long wardrobeId) {
        if (!accessService.checkAccessToWardrobe(userId, wardrobeId)) {
            return false;
        }
        wardrobeRepository.findWardrobeById(wardrobeId).ifPresent(wardrobe -> {
            wardrobe.setIsDeleted(true);
            wardrobeRepository.save(wardrobe);
        });
        return true;
    }
}
