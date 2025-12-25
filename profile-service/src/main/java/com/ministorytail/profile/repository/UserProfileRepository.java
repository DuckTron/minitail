package com.ministorytail.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ministorytail.profile.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Busca perfil pelo userId (FK do Auth Service)
     * Este é o método mais usado - Story Service busca por userId, não por id
     * interno
     */
    Optional<UserProfile> findByUserId(Long userId);

    /**
     * Verifica se já existe perfil para este userId
     * Usado para evitar duplicação
     */
    boolean existsByUserId(Long userId);
}