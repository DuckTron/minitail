package com.ministorytail.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected User() {
    }

    private User(String name, String normalizedEmail, String normalizedPhone, String passwordHash) {
        this.name = name;
        this.email = normalizedEmail;
        this.phone = normalizedPhone;
        this.password = passwordHash;
    }

    public static User register(
            String name,
            String email,
            String phone,
            String rawPassword,
            UserUniquenessChecker uniquenessChecker,
            PasswordHasher passwordHasher) {
        validateName(name);
        validateEmail(email);
        validatePassword(rawPassword);

        String normalizedEmail = normalizeEmail(email);
        String normalizedPhone = normalizePhone(phone);

        if (uniquenessChecker.emailExists(normalizedEmail)) {
            throw new EmailAlreadyRegisteredException();
        }

        if (normalizedPhone != null && uniquenessChecker.phoneExists(normalizedPhone)) {
            throw new PhoneAlreadyRegisteredException();
        }

        String passwordHash = passwordHasher.hash(rawPassword);

        return new User(
                name.trim(),
                normalizedEmail,
                normalizedPhone,
                passwordHash);
    }

    public boolean credentialsValid(String rawPassword, PasswordHasher passwordHasher) {
        if (rawPassword == null || rawPassword.isBlank()) {
            return false;
        }
        return passwordHasher.matches(rawPassword, this.password);
    }

    public static String normalizeEmail(String email) {
        return email == null ? null : email.toLowerCase().trim();
    }

    public static String normalizePhone(String phone) {
        if (phone == null) {
            return null;
        }
        String p = phone.trim();
        return p.isEmpty() ? null : p;
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        String e = email.trim();
        if (!e.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
