package com.ministorytail.profile.model.exceptions;

public class ChildNotFoundException extends RuntimeException {
    public ChildNotFoundException(String message) {
        super(message);
    }

    public ChildNotFoundException(Long childId) {
        super(String.format("Child not found with id: %d", childId));
    }
}