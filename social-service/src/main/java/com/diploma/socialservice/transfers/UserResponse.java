package com.diploma.socialservice.transfers;

import com.diploma.socialservice.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String bio;
    private String avatar;

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(),
                user.getUsername(),
                user.getBio(),
                user.getAvatar());
    }
}
