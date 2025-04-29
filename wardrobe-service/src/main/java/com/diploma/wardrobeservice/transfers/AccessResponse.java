package com.diploma.wardrobeservice.transfers;

import com.diploma.wardrobeservice.entities.AccessPermission;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccessResponse {
    private Long id;
    private Long wardrobeId;
    private Long grantedToUserId;
    private String accessType;

    public static AccessResponse from(AccessPermission access) {
        return new AccessResponse(
                access.getId(),
                access.getWardrobe().getId(),
                access.getGrantedToUserId(),
                access.getAccessType()
        );
    }
}
