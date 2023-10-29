package org.alx.fitnessapp.model.dto;

import lombok.Getter;

@Getter
public enum BodyTypeGoalEnum {
    LOSE_WEIGHT("Lose weight"),
    MAINTAIN_WEIGHT("Maintain weight"),
    GAIN_WEIGHT("Gain weight");

    private final String value;

    BodyTypeGoalEnum(String value) {
        this.value = value;
    }

}
