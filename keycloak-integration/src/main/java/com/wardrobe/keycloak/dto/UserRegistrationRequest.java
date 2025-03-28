package com.wardrobe.keycloak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
    private String username;
    private String email;
    private String password;
}
