package com.ministorytail.profile.dto;

import java.time.LocalDate;

import com.ministorytail.profile.model.Plan;
import com.ministorytail.profile.model.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long userId;
    private String displayName;
    private String avatarUrl;
    private Plan plan;
    private Integer monthlyLimit;
    private Integer booksGeneratedThisMonth;
    private LocalDate currentPeriodStart;
    private LocalDate currentPeriodEnd;

    // Factory method para converter do domínio
    public static ProfileResponse fromDomain(UserProfile profile) {
        return new ProfileResponse(
                profile.getUserId(),
                profile.getDisplayName(),
                profile.getAvatarUrl(),
                profile.getPlan(),
                profile.getMonthlyLimit(),
                profile.getBooksGeneratedThisMonth(),
                profile.getCurrentPeriodStart(),
                profile.getCurrentPeriodEnd());
    }
}