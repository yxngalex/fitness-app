package org.alx.fitnessapp.model.dto;


import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("Male"),
    FEMALE("Female");

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }

}
