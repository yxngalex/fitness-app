package org.alx.fitnessapp.model.dto;

import lombok.Data;

@Data
public class ExerciseStatDTO {
    private Integer set;
    private Integer reps;
    private Double exerciseWeight;
    private ExerciseDTO exerciseDTO;
}
