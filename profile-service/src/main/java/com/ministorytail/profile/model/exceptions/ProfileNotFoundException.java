package com.ministorytail.profile.model.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException(Long userId) {
        super(String.format("Profile not found for userId: %d", userId));
    }
}