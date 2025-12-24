package com.ministorytail.auth.model;

public interface PasswordHasher {
    String hash(String raw);
    boolean matches(String raw, String hash);
}
