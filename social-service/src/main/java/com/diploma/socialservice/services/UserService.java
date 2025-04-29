package com.diploma.socialservice.services;

import com.diploma.socialservice.entities.User;
import com.diploma.socialservice.exception.ResourceNotFoundException;
import com.diploma.socialservice.repositories.UserRepository;
import com.diploma.socialservice.transfers.UserCreateRequest;
import com.diploma.socialservice.transfers.UserResponse;
import com.diploma.socialservice.transfers.UserUpdateAvatarRequest;
import com.diploma.socialservice.transfers.UserUpdateBioRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<UserResponse> findUserByUsername(String username) {
        List<User> users = userRepository.findByUsernameStartingWith(username);
        return users.stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    public void createUser(Long userId, UserCreateRequest request) {
        User user = User.builder()
                .id(userId)
                .username(request.getUsername())
                .build();

        userRepository.save(user);
    }

    public void updateBio(Long userId, UserUpdateBioRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setBio(request.getBio());

        userRepository.save(user);
    }

    public void updateAvatar(Long userId, UserUpdateAvatarRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setAvatar(request.getAvatar());

        userRepository.save(user);
    }
}
