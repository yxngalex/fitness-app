package org.alx.fitnessapp.model.dto;
public enum CategoryEnum {
    CARDIO("Cardio"),
    BREAK("Break"),
    LEGS("Legs"),
    BODYWEIGHT("Bodyweight"),
    PUSH("Push"),
    PULL("Pull");

    private final String categoryName;

    CategoryEnum(String categoryName) {
        this.categoryName = categoryName;
    }

}
