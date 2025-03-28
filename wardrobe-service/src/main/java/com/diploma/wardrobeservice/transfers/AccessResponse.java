package com.diploma.wardrobeservice.transfers;

import lombok.Data;

@Data
public class AccessResponse {
    private Long id;

    private Long wardrobeId;

    private String grantedToUserId;

    private String accessType;
}
