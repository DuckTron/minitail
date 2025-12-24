package com.ministorytail.auth.model;

public class PhoneAlreadyRegisteredException extends RuntimeException {
    public PhoneAlreadyRegisteredException() {
        super("Phone already registered");
    }
}
