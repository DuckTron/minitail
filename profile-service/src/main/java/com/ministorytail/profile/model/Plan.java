package com.ministorytail.profile.model;

public enum Plan {
    BASIC(15),
    INTERMEDIATE(30),
    ADVANCED(40);

    private final int monthlyLimit;

    Plan(int monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public int getMonthlyLimit() {
        return monthlyLimit;
    }
}