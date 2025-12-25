package com.ministorytail.profile.model.exceptions;

public class ChildAlreadyExistsException extends RuntimeException {
    public ChildAlreadyExistsException(String message) {
        super(message);
    }

    public ChildAlreadyExistsException(Long parentUserId, String childName) {
        super(String.format("Child with name '%s' already exists for parent userId: %d",
                childName, parentUserId));
    }
}