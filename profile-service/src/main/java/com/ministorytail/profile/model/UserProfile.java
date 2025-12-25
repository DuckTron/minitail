package com.ministorytail.profile.model;

import java.time.LocalDate;

import com.ministorytail.profile.model.exceptions.MonthlyLimitExceededException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId; // FK para Auth Service

    @Column(length = 100)
    private String displayName;

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Plan plan;

    @Column(nullable = false)
    private Integer monthlyLimit;

    @Column(nullable = false)
    private Integer booksGeneratedThisMonth = 0;

    @Column(nullable = false)
    private LocalDate currentPeriodStart;

    @Column(nullable = false)
    private LocalDate currentPeriodEnd;

    // Factory method
    public static UserProfile create(Long userId, String displayName, Plan plan) {
        UserProfile profile = new UserProfile();
        profile.userId = userId;
        profile.displayName = displayName;
        profile.plan = plan;
        profile.monthlyLimit = plan.getMonthlyLimit();
        profile.booksGeneratedThisMonth = 0;
        profile.currentPeriodStart = LocalDate.now();
        profile.currentPeriodEnd = LocalDate.now().plusMonths(1);
        return profile;
    }

    // Método de domínio: valida se pode gerar livro
    public boolean canGenerateBook() {
        resetIfNeeded();
        return booksGeneratedThisMonth < monthlyLimit;
    }

    // Método de domínio: incrementa contador
    public void incrementBookCount() {
        resetIfNeeded();

        if (!canGenerateBook()) {
            throw new MonthlyLimitExceededException(
                    String.format("Monthly limit of %d books exceeded", monthlyLimit));
        }

        this.booksGeneratedThisMonth++;
    }

    // Método de domínio: atualiza plano
    public void updatePlan(Plan newPlan) {
        this.plan = newPlan;
        this.monthlyLimit = newPlan.getMonthlyLimit();
    }

    // Método de domínio: atualiza perfil
    public void updateProfile(String displayName, String avatarUrl) {
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
    }

    // Reset automático
    private void resetIfNeeded() {
        LocalDate today = LocalDate.now();

        if (today.isAfter(currentPeriodEnd)) {
            this.booksGeneratedThisMonth = 0;
            this.currentPeriodStart = today;
            this.currentPeriodEnd = today.plusMonths(1);
        }
    }
}