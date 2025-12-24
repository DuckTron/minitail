package com.ministorytail.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ministorytail.auth.dto.AuthResponse;
import com.ministorytail.auth.dto.LoginRequest;
import com.ministorytail.auth.dto.RegisterRequest;
import com.ministorytail.auth.model.InvalidCredentialsException;
import com.ministorytail.auth.model.PasswordHasher;
import com.ministorytail.auth.model.User;
import com.ministorytail.auth.model.UserUniquenessChecker;
import com.ministorytail.auth.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        User user = User.register(
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getPassword(),
                uniquenessChecker(),
                passwordHasher());

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                "User registered successfully");
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = User.normalizeEmail(request.getEmail());

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.credentialsValid(request.getPassword(), passwordHasher())) {
            throw new InvalidCredentialsException();
        }

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                "Login successful");
    }

    private UserUniquenessChecker uniquenessChecker() {
        return new UserUniquenessChecker() {
            @Override
            public boolean emailExists(String normalizedEmail) {
                return userRepository.existsByEmail(normalizedEmail);
            }

            @Override
            public boolean phoneExists(String normalizedPhone) {
                return userRepository.existsByPhone(normalizedPhone);
            }
        };
    }

    private PasswordHasher passwordHasher() {
        return new PasswordHasher() {
            @Override
            public String hash(String raw) {
                return passwordEncoder.encode(raw);
            }

            @Override
            public boolean matches(String raw, String hash) {
                return passwordEncoder.matches(raw, hash);
            }
        };
    }
}
