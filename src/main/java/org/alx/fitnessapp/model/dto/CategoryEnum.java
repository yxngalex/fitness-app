package org.alx.fitnessapp.model.dto;

public enum CategoryEnum {
    CARDIO("Cardio"),
    BREAK("Break"),
    LEGS("Legs"),
    BODYWEIGHT("Bodyweight"),
    PUSH("Push"),
    PULL("Pull"),
    SHOULDERS("Shoulders"),
    MIXED("Mixed");

    private final String value;

    CategoryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
