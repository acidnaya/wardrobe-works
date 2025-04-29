package com.diploma.gateway.service;

import com.diploma.gateway.clients.SocialServiceClient;
import com.diploma.gateway.clients.WardrobeServiceClient;
import com.diploma.gateway.dto.AuthRequest;
import com.diploma.gateway.dto.AuthResponse;
import com.diploma.gateway.dto.RegisterRequest;
import com.diploma.gateway.dto.UserCreateRequest;
import com.diploma.gateway.entity.User;
import com.diploma.gateway.jwt.JwtUtil;
import com.diploma.gateway.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final SocialServiceClient socialServiceClient;
    private final WardrobeServiceClient wardrobeServiceClient;

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        try {
            socialServiceClient.createUser(user.getId(), new UserCreateRequest(request.getUsername()));
        } catch (FeignException e) {
            log.error("Ошибка при создании пользователя в social-service: {}", e.getMessage());
            userRepository.deleteById(user.getId());
            throw new RuntimeException("Ошибка регистрации. Попробуйте позже");
        }

        try {
            wardrobeServiceClient.createCalendar(user.getId());
        } catch (FeignException e) {
            log.error("Ошибка при создании гардероба в wardrobe-service: {}", e.getMessage());
            userRepository.deleteById(user.getId());
            throw new RuntimeException("Ошибка регистрации. Попробуйте позже");
        }

    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Пользователя с такой почтой не существует"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Неправильный пароль");
        }

        String token = jwtUtil.generateToken(user.getId());

        return new AuthResponse(token);
    }
}
