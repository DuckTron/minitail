package com.ministorytail.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ministorytail.auth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // método para buscar usuário por email e garantir unicidade
    Optional<User> findByEmail(String email);
    // método para verificar existência de usuário por email
    boolean existsByEmail(String email);
}