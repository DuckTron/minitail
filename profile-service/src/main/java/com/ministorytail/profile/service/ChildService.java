package com.ministorytail.profile.service;

import com.ministorytail.profile.model.Child;
import com.ministorytail.profile.model.exceptions.ChildAlreadyExistsException;
import com.ministorytail.profile.model.exceptions.ChildNotFoundException;
import com.ministorytail.profile.repository.ChildRepository;
import com.ministorytail.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChildService {

    private final ChildRepository childRepository;
    private final UserProfileRepository profileRepository;

    /**
     * Cria filho para um responsável
     */
    @Transactional
    public Child createChild(Long parentUserId, String name, Integer age, List<String> interests) {
        log.debug("Creating child '{}' for parentUserId: {}", name, parentUserId);

        // Validar que o pai existe (tem perfil)
        if (!profileRepository.existsByUserId(parentUserId)) {
            throw new IllegalStateException(
                    String.format("Parent profile not found for userId: %d. Create profile first.", parentUserId));
        }

        // Validar nome duplicado (opcional - você decide se quer essa regra)
        if (childRepository.existsByParentUserIdAndName(parentUserId, name.trim())) {
            throw new ChildAlreadyExistsException(parentUserId, name);
        }

        // Factory method do agregado cria com invariantes válidas
        Child child = Child.create(parentUserId, name, age, interests);

        Child saved = childRepository.save(child);
        log.info("Child created successfully: id={}, name='{}', parentUserId={}",
                saved.getId(), saved.getName(), parentUserId);

        return saved;
    }

    /**
     * Busca filho por ID (usado por Story Service)
     */
    @Transactional(readOnly = true)
    public Child getChildById(Long childId) {
        log.debug("Fetching child with id: {}", childId);

        return childRepository.findById(childId)
                .orElseThrow(() -> new ChildNotFoundException(childId));
    }

    /**
     * Busca filho validando que pertence ao pai (segurança)
     */
    @Transactional(readOnly = true)
    public Child getChildByIdAndParent(Long childId, Long parentUserId) {
        log.debug("Fetching child id={} for parentUserId={}", childId, parentUserId);

        return childRepository.findByIdAndParentUserId(childId, parentUserId)
                .orElseThrow(() -> new ChildNotFoundException(
                        String.format("Child %d not found or does not belong to parent %d", childId, parentUserId)));
    }

    /**
     * Lista todos os filhos de um responsável
     */
    @Transactional(readOnly = true)
    public List<Child> getChildrenByParent(Long parentUserId) {
        log.debug("Fetching all children for parentUserId: {}", parentUserId);

        // Validar que o pai existe
        if (!profileRepository.existsByUserId(parentUserId)) {
            throw new IllegalStateException(
                    String.format("Parent profile not found for userId: %d", parentUserId));
        }

        List<Child> children = childRepository.findByParentUserId(parentUserId);
        log.info("Found {} children for parentUserId: {}", children.size(), parentUserId);

        return children;
    }

    /**
     * Atualiza dados do filho
     */
    @Transactional
    public Child updateChild(Long childId, Long parentUserId, String name, Integer age, List<String> interests) {
        log.debug("Updating child id={} for parentUserId={}", childId, parentUserId);

        // Busca validando ownership
        Child child = getChildByIdAndParent(childId, parentUserId);

        // Validar nome duplicado se mudou o nome (opcional)
        if (!child.getName().equals(name.trim()) &&
                childRepository.existsByParentUserIdAndName(parentUserId, name.trim())) {
            throw new ChildAlreadyExistsException(parentUserId, name);
        }

        // Método de domínio valida invariantes e atualiza
        child.update(name, age, interests);

        Child updated = childRepository.save(child);
        log.info("Child updated successfully: id={}, name='{}'", updated.getId(), updated.getName());

        return updated;
    }

    /**
     * Deleta filho
     */
    @Transactional
    public void deleteChild(Long childId, Long parentUserId) {
        log.debug("Deleting child id={} for parentUserId={}", childId, parentUserId);

        // Busca validando ownership
        Child child = getChildByIdAndParent(childId, parentUserId);

        childRepository.delete(child);
        log.info("Child deleted successfully: id={}, name='{}'", child.getId(), child.getName());
    }

    /**
     * Adiciona interesse a um filho
     */
    @Transactional
    public Child addInterest(Long childId, Long parentUserId, String interest) {
        log.debug("Adding interest '{}' to child id={}", interest, childId);

        Child child = getChildByIdAndParent(childId, parentUserId);

        // Método de domínio valida e adiciona
        child.addInterest(interest);

        Child updated = childRepository.save(child);
        log.info("Interest added to child id={}", childId);

        return updated;
    }

    /**
     * Remove interesse de um filho
     */
    @Transactional
    public Child removeInterest(Long childId, Long parentUserId, String interest) {
        log.debug("Removing interest '{}' from child id={}", interest, childId);

        Child child = getChildByIdAndParent(childId, parentUserId);

        // Método de domínio valida (precisa ter pelo menos 1) e remove
        child.removeInterest(interest);

        Child updated = childRepository.save(child);
        log.info("Interest removed from child id={}", childId);

        return updated;
    }

    /**
     * Conta quantos filhos o responsável tem
     */
    @Transactional(readOnly = true)
    public long countChildrenByParent(Long parentUserId) {
        return childRepository.countByParentUserId(parentUserId);
    }
}