package com.ministorytail.profile.service;

import com.ministorytail.profile.model.Plan;
import com.ministorytail.profile.model.UserProfile;
import com.ministorytail.profile.model.exceptions.ProfileNotFoundException;
import com.ministorytail.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final UserProfileRepository profileRepository;

    /**
     * Cria perfil para um responsável (chamado após registro no Auth Service)
     */
    @Transactional
    public UserProfile createProfile(Long userId, String displayName, Plan plan) {
        log.debug("Creating profile for userId: {}", userId);

        // Validar se já existe
        if (profileRepository.existsByUserId(userId)) {
            throw new IllegalStateException(
                    String.format("Profile already exists for userId: %d", userId));
        }

        // Factory method do agregado cria com invariantes válidas
        UserProfile profile = UserProfile.create(userId, displayName, plan);

        UserProfile saved = profileRepository.save(profile);
        log.info("Profile created successfully for userId: {}", userId);

        return saved;
    }

    /**
     * Busca perfil por userId (usado por outros serviços)
     */
    @Transactional(readOnly = true)
    public UserProfile getProfileByUserId(Long userId) {
        log.debug("Fetching profile for userId: {}", userId);

        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException(userId));
    }

    /**
     * Atualiza dados do perfil
     */
    @Transactional
    public UserProfile updateProfile(Long userId, String displayName, String avatarUrl) {
        log.debug("Updating profile for userId: {}", userId);

        UserProfile profile = getProfileByUserId(userId);

        // Método de domínio faz a atualização
        profile.updateProfile(displayName, avatarUrl);

        UserProfile updated = profileRepository.save(profile);
        log.info("Profile updated successfully for userId: {}", userId);

        return updated;
    }

    /**
     * Atualiza plano de assinatura
     */
    @Transactional
    public UserProfile updatePlan(Long userId, Plan newPlan) {
        log.debug("Updating plan to {} for userId: {}", newPlan, userId);

        UserProfile profile = getProfileByUserId(userId);

        // Método de domínio atualiza plano e limite
        profile.updatePlan(newPlan);

        UserProfile updated = profileRepository.save(profile);
        log.info("Plan updated successfully to {} for userId: {}", newPlan, userId);

        return updated;
    }

    /**
     * Incrementa contador de livros gerados (chamado pelo Story Service)
     */
    @Transactional
    public void incrementBookCount(Long userId) {
        log.debug("Incrementing book count for userId: {}", userId);

        UserProfile profile = getProfileByUserId(userId);

        // Método de domínio valida limite e incrementa
        // Pode lançar MonthlyLimitExceededException
        profile.incrementBookCount();

        profileRepository.save(profile);
        log.info("Book count incremented for userId: {}, current: {}/{}",
                userId, profile.getBooksGeneratedThisMonth(), profile.getMonthlyLimit());
    }

    /**
     * Verifica se pode gerar livro (usado pelo Story Service antes de iniciar
     * geração)
     */
    @Transactional(readOnly = true)
    public boolean canGenerateBook(Long userId) {
        log.debug("Checking if userId {} can generate book", userId);

        UserProfile profile = getProfileByUserId(userId);

        // Método de domínio valida (inclui reset automático se necessário)
        return profile.canGenerateBook();
    }
}