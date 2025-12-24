package com.ministorytail.auth.model;

public interface UserUniquenessChecker {
    boolean emailExists(String normalizedEmail);
    boolean phoneExists(String normalizedPhone);
}
