package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.entities.*;
import com.diploma.wardrobeservice.exceptions.ResourceNotAccessibleException;
import com.diploma.wardrobeservice.exceptions.ResourceNotFoundException;
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
    private final WardrobeRepository wardrobeRepository;

    public List<AccessResponse> getAll(Long userId) {
        return accessRepository.findByWardrobe_CreatorId(userId)
                .stream()
                .map(AccessResponse::from)
                .toList();
    }

    public void grant(Long userId, AccessCreateRequest request) {
        var wardrobe = wardrobeRepository.findWardrobeByIdAndIsDeletedFalse(request.getWardrobeId())
                .orElseThrow(() -> new ResourceNotFoundException("Wardrobe not found with id: " + request.getWardrobeId()));
        checkFullEditAccessThrow(userId, wardrobe);

        AccessPermission permission = new AccessPermission();
        permission.setAccessType(request.getAccessType());
        permission.setGrantedToUserId(request.getGrantedToUserId());
        permission.setWardrobe(wardrobe);
        accessRepository.save(permission);
    }

    public void revoke(Long userId, Long accessId) {
        if (!accessRepository.existsByIdAndGrantedToUserId(accessId, userId)) {
            throw new ResourceNotFoundException("Access not found");
        }
        accessRepository.deleteByIdAndGrantedToUserId(accessId, userId);
    }

    public boolean checkEditAccess(Long userId, Wardrobe wardrobe) {
        if (wardrobe.getCreatorId().equals(userId)) {
            return true;
        }
        return accessRepository.findByWardrobeAndGrantedToUserId(wardrobe, userId).isPresent();
    }

    public boolean checkViewAccess(Long userId, Wardrobe wardrobe) {
        if (wardrobe.getCreatorId().equals(userId)) {
            return true;
        }
        if (!wardrobe.getIsPrivate()) {
            return true;
        }
        return checkEditAccess(userId, wardrobe);
    }

    public void checkEditAccessThrow(Long userId, Wardrobe wardrobe) {
        if (!checkEditAccess(userId, wardrobe)) {
            throw new ResourceNotAccessibleException("User does not have edit access to this entity");
        }
    }

    public void checkViewAccessThrow(Long userId, Wardrobe wardrobe) {
        if (!checkViewAccess(userId, wardrobe)) {
            throw new ResourceNotAccessibleException("User does not have view access to this entity");
        }
    }

    public void checkFullEditAccessThrow(Long userId, Wardrobe wardrobe) {
        if (!wardrobe.getCreatorId().equals(userId)) {
            throw new ResourceNotAccessibleException("User does not have full edit access to this entity");
        }
    }

    public void checkCalendarFullAccessThrow(Long userId, Calendar calendar) {
        if (!calendar.getUserId().equals(userId)) {
            throw new ResourceNotAccessibleException("User does not have full edit access to this calendar");
        }
    }
    public void checkCalendarViewAccessThrow(Long userId, Calendar calendar) {
        if (!calendar.getUserId().equals(userId) && calendar.getIsPrivate()) {
            throw new ResourceNotAccessibleException("User does not have view access to this calendar");
        }
    }


}
