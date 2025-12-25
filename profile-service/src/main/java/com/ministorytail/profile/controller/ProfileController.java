package com.ministorytail.profile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ministorytail.profile.dto.CreateProfileRequest;
import com.ministorytail.profile.dto.ProfileResponse;
import com.ministorytail.profile.dto.UpdatePlanRequest;
import com.ministorytail.profile.dto.UpdateProfileRequest;
import com.ministorytail.profile.model.UserProfile;
import com.ministorytail.profile.service.ProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    /**
     * POST /api/profile
     * Cria perfil para um responsável (chamado após registro no Auth)
     */
    @PostMapping
    public ResponseEntity<ProfileResponse> createProfile(@Valid @RequestBody CreateProfileRequest request) {
        log.info("POST /api/profile - Creating profile for userId: {}", request.getUserId());

        UserProfile profile = profileService.createProfile(
                request.getUserId(),
                request.getDisplayName(),
                request.getPlan());

        ProfileResponse response = ProfileResponse.fromDomain(profile);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/profile/{userId}
     * Busca perfil por userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long userId) {
        log.info("GET /api/profile/{} - Fetching profile", userId);

        UserProfile profile = profileService.getProfileByUserId(userId);
        ProfileResponse response = ProfileResponse.fromDomain(profile);

        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/profile/{userId}
     * Atualiza dados do perfil
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ProfileResponse> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateProfileRequest request) {

        log.info("PUT /api/profile/{} - Updating profile", userId);

        UserProfile profile = profileService.updateProfile(
                userId,
                request.getDisplayName(),
                request.getAvatarUrl());

        ProfileResponse response = ProfileResponse.fromDomain(profile);

        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/profile/{userId}/plan
     * Atualiza plano de assinatura
     */
    @PatchMapping("/{userId}/plan")
    public ResponseEntity<ProfileResponse> updatePlan(
            @PathVariable Long userId,
            @Valid @RequestBody UpdatePlanRequest request) {

        log.info("PATCH /api/profile/{}/plan - Updating plan to {}", userId, request.getPlan());

        UserProfile profile = profileService.updatePlan(userId, request.getPlan());
        ProfileResponse response = ProfileResponse.fromDomain(profile);

        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/profile/{userId}/increment-book
     * Incrementa contador de livros (chamado pelo Story Service)
     */
    @PatchMapping("/{userId}/increment-book")
    public ResponseEntity<Void> incrementBookCount(@PathVariable Long userId) {
        log.info("PATCH /api/profile/{}/increment-book - Incrementing book count", userId);

        profileService.incrementBookCount(userId);

        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/profile/{userId}/can-generate
     * Verifica se pode gerar livro (usado pelo Story Service)
     */
    @GetMapping("/{userId}/can-generate")
    public ResponseEntity<Boolean> canGenerateBook(@PathVariable Long userId) {
        log.info("GET /api/profile/{}/can-generate - Checking if can generate book", userId);

        boolean canGenerate = profileService.canGenerateBook(userId);

        return ResponseEntity.ok(canGenerate);
    }
}