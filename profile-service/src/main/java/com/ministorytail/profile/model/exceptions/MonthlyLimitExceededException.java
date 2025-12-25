package com.ministorytail.profile.model.exceptions;

public class MonthlyLimitExceededException extends RuntimeException {
    public MonthlyLimitExceededException(String message) {
        super(message);
    }
}