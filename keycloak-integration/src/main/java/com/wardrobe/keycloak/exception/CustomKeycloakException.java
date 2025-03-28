package com.wardrobe.keycloak.exception;

public class CustomKeycloakException extends RuntimeException {
    private final int statusCode;

    public CustomKeycloakException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
