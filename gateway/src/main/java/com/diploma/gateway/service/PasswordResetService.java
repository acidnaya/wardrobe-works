package com.diploma.gateway.service;

import com.diploma.gateway.entity.PasswordReset;
import com.diploma.gateway.entity.User;
import com.diploma.gateway.repository.PasswordResetRepository;
import com.diploma.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void sendResetLink(String toEmail) {
        User user = userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();

        PasswordReset resetToken = new PasswordReset();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiresAt(OffsetDateTime.now().plusMinutes(10));
        resetToken.setIsUsed(false);

        tokenRepository.save(resetToken);

        String resetLink = "https://gate-acidnaya.amvera.io/auth/reset-password?token=" + token;
        String resetDeepink = "wardrobeworks://reset-password?token=" + token;

        emailService.sendPasswordResetEmail(toEmail, resetLink, resetDeepink);
    }

    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        System.out.println("In method");
        PasswordReset resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getIsUsed() || resetToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            return false;
        }

        System.out.println("Getting user");
        User user = resetToken.getUser();
        System.out.println("Got user");
        System.out.println("User = " + user.getId());
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setIsUsed(true);
        tokenRepository.save(resetToken);
        return true;
    }
}