package com.ministorytail.auth.service;

import org.springframework.stereotype.Service;

import com.ministorytail.auth.dto.AuthResponse;
import com.ministorytail.auth.dto.LoginRequest;
import com.ministorytail.auth.dto.RegisterRequest;
import com.ministorytail.auth.model.User;
import com.ministorytail.auth.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                "Usuário registrado com sucesso");
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                "Login realizado com sucesso");
    }
}