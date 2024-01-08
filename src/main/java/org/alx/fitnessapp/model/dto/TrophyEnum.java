package org.alx.fitnessapp.model.dto;

public enum TrophyEnum {
    WELCOME("Welcome");

    private final String value;

    TrophyEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return WELCOME.name();
    }
}
