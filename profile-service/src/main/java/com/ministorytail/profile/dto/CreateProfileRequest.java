package com.ministorytail.profile.dto;

import com.ministorytail.profile.model.Plan;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileRequest {

    @NotNull(message = "UserId is required")
    private Long userId;

    private String displayName; // Opcional

    @NotNull(message = "Plan is required")
    private Plan plan;
}