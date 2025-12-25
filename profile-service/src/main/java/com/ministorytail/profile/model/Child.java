package com.ministorytail.profile.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "children")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long parentUserId; // FK para UserProfile (userId do Auth)

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "child_interests", joinColumns = @JoinColumn(name = "child_id"))
    @Column(name = "interest")
    private List<String> interests = new ArrayList<>();

    // Factory method - forma correta de criar Child
    public static Child create(Long parentUserId, String name, Integer age, List<String> interests) {
        validateInputs(name, age, interests);

        Child child = new Child();
        child.parentUserId = parentUserId;
        child.name = name.trim();
        child.age = age;
        child.interests = new ArrayList<>(interests); // Cópia defensiva
        return child;
    }

    // Método de domínio: atualizar dados do filho
    public void update(String name, Integer age, List<String> interests) {
        validateInputs(name, age, interests);

        this.name = name.trim();
        this.age = age;
        this.interests = new ArrayList<>(interests);
    }

    // Método de domínio: adicionar interesse
    public void addInterest(String interest) {
        if (interest == null || interest.trim().isEmpty()) {
            throw new IllegalArgumentException("Interest cannot be empty");
        }

        String trimmedInterest = interest.trim();
        if (!this.interests.contains(trimmedInterest)) {
            this.interests.add(trimmedInterest);
        }
    }

    // Método de domínio: remover interesse
    public void removeInterest(String interest) {
        if (this.interests.size() <= 1) {
            throw new IllegalStateException("Child must have at least one interest");
        }

        this.interests.remove(interest);
    }

    // Validações de invariantes
    private static void validateInputs(String name, Integer age, List<String> interests) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Child name cannot be empty");
        }

        if (age == null || age <= 0) {
            throw new IllegalArgumentException("Child age must be greater than 0");
        }

        if (interests == null || interests.isEmpty()) {
            throw new IllegalArgumentException("Child must have at least one interest");
        }

        // Valida que nenhum interesse é vazio
        boolean hasEmptyInterest = interests.stream()
                .anyMatch(i -> i == null || i.trim().isEmpty());

        if (hasEmptyInterest) {
            throw new IllegalArgumentException("Interests cannot contain empty values");
        }
    }
}