package org.alx.fitnessapp.model.dto;

public enum TrophyEnum {
    IT_BEGINS_NOW("It begins now.");

    private final String value;

    TrophyEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return IT_BEGINS_NOW.name();
    }
}
