package org.alx.fitnessapp.model.dto;

import lombok.Data;

@Data
public class ExerciseStatsDTO {
    private Integer set;
    private Integer reps;
    private Double exerciseWeight;
    private ExerciseDTO exerciseDTO;
}
