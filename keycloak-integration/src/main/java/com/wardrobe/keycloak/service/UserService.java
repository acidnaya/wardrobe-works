package com.wardrobe.keycloak.service;

import com.wardrobe.keycloak.dto.RegisterResponse;
import com.wardrobe.keycloak.exception.CustomKeycloakException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import jakarta.ws.rs.core.Response;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Keycloak keycloak;

    private static final String TOKEN_URL = "http://localhost:9090/realms/wardrobe-app/protocol/openid-connect/token";
    private static final String CLIENT_ID = "backend-client";

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin-client-id}")
    private String adminClientId;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    public String getAccessToken(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("grant_type", "password");
        requestBody.add("username", username);
        requestBody.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    TOKEN_URL, HttpMethod.POST, request, Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody().get("access_token").toString();
            } else {
                throw new RuntimeException("Failed to retrieve token: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Authentication failed: " + e.getResponseBodyAsString());
        }
    }

    public RegisterResponse registerUser(String username, String email, String password) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm("master")
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .grantType("password")
                .build();

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setEnabled(true);
        user.setEmailVerified(true); // Optional

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            return new RegisterResponse("User registered successfully.", 201);
        } else {
            String errorResponse = response.readEntity(String.class);
            throw new CustomKeycloakException(errorResponse, response.getStatus());
        }
    }
}
