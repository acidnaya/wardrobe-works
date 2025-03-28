package com.wardrobe.keycloak.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterErrorResponse {
    private String message;
    private int statusCode;
}
