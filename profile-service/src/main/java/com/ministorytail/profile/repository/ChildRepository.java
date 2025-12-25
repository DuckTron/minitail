package com.ministorytail.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ministorytail.profile.model.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    /**
     * Busca todos os filhos de um responsável
     * Usado no endpoint GET /api/profile/{userId}/children
     */
    List<Child> findByParentUserId(Long parentUserId);

    /**
     * Busca filho específico de um responsável
     * Valida que o filho realmente pertence ao pai (segurança)
     */
    Optional<Child> findByIdAndParentUserId(Long childId, Long parentUserId);

    /**
     * Verifica se já existe filho com este nome para este pai
     * Opcional: pode ser usado para evitar nomes duplicados
     */
    boolean existsByParentUserIdAndName(Long parentUserId, String name);

    /**
     * Conta quantos filhos o responsável tem
     * Útil para validações ou estatísticas
     */
    long countByParentUserId(Long parentUserId);
}