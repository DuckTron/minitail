package com.ministorytail.profile.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ministorytail.profile.dto.ChildResponse;
import com.ministorytail.profile.dto.CreateChildRequest;
import com.ministorytail.profile.dto.UpdateChildRequest;
import com.ministorytail.profile.model.Child;
import com.ministorytail.profile.service.ChildService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class ChildController {

    private final ChildService childService;

    /**
     * POST /api/profile/{userId}/children
     * Cadastra filho para um responsável
     */
    @PostMapping("/{userId}/children")
    public ResponseEntity<ChildResponse> createChild(
            @PathVariable Long userId,
            @Valid @RequestBody CreateChildRequest request) {

        log.info("POST /api/profile/{}/children - Creating child '{}'", userId, request.getName());

        Child child = childService.createChild(
                userId,
                request.getName(),
                request.getAge(),
                request.getInterests());

        ChildResponse response = ChildResponse.fromDomain(child);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/profile/{userId}/children
     * Lista todos os filhos de um responsável
     */
    @GetMapping("/{userId}/children")
    public ResponseEntity<List<ChildResponse>> getChildrenByParent(@PathVariable Long userId) {
        log.info("GET /api/profile/{}/children - Fetching all children", userId);

        List<Child> children = childService.getChildrenByParent(userId);

        List<ChildResponse> response = children.stream()
                .map(ChildResponse::fromDomain)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/profile/children/{childId}
     * Busca filho específico (usado pelo Story Service)
     */
    @GetMapping("/children/{childId}")
    public ResponseEntity<ChildResponse> getChild(@PathVariable Long childId) {
        log.info("GET /api/profile/children/{} - Fetching child", childId);

        Child child = childService.getChildById(childId);
        ChildResponse response = ChildResponse.fromDomain(child);

        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/profile/children/{childId}
     * Atualiza dados do filho
     * Query param: parentUserId (validação de ownership)
     */
    @PutMapping("/children/{childId}")
    public ResponseEntity<ChildResponse> updateChild(
            @PathVariable Long childId,
            @RequestParam Long parentUserId,
            @Valid @RequestBody UpdateChildRequest request) {

        log.info("PUT /api/profile/children/{} - Updating child (parent: {})", childId, parentUserId);

        Child child = childService.updateChild(
                childId,
                parentUserId,
                request.getName(),
                request.getAge(),
                request.getInterests());

        ChildResponse response = ChildResponse.fromDomain(child);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/profile/children/{childId}
     * Deleta filho
     * Query param: parentUserId (validação de ownership)
     */
    @DeleteMapping("/children/{childId}")
    public ResponseEntity<Void> deleteChild(
            @PathVariable Long childId,
            @RequestParam Long parentUserId) {

        log.info("DELETE /api/profile/children/{} - Deleting child (parent: {})", childId, parentUserId);

        childService.deleteChild(childId, parentUserId);

        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/profile/children/{childId}/interests
     * Adiciona interesse a um filho
     */
    @PostMapping("/children/{childId}/interests")
    public ResponseEntity<ChildResponse> addInterest(
            @PathVariable Long childId,
            @RequestParam Long parentUserId,
            @RequestParam String interest) {

        log.info("POST /api/profile/children/{}/interests - Adding interest '{}'", childId, interest);

        Child child = childService.addInterest(childId, parentUserId, interest);
        ChildResponse response = ChildResponse.fromDomain(child);

        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/profile/children/{childId}/interests
     * Remove interesse de um filho
     */
    @DeleteMapping("/children/{childId}/interests")
    public ResponseEntity<ChildResponse> removeInterest(
            @PathVariable Long childId,
            @RequestParam Long parentUserId,
            @RequestParam String interest) {

        log.info("DELETE /api/profile/children/{}/interests - Removing interest '{}'", childId, interest);

        Child child = childService.removeInterest(childId, parentUserId, interest);
        ChildResponse response = ChildResponse.fromDomain(child);

        return ResponseEntity.ok(response);
    }
}