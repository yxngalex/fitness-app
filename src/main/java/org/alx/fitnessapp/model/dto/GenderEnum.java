package org.alx.fitnessapp.model.dto;


import lombok.Getter;

@Getter
public enum GenderEnum {
    MAN("Man"),
    WOMAN("Woman");

    private final String value;

    GenderEnum(String value) {
        this.value = value;
    }

}
