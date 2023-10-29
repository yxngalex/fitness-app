package org.alx.fitnessapp.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayDTO {
    private NutritionDTO nutritionDTO;
    private WorkoutRoutineDTO workoutRoutineDTO;
    private LocalDate loggedDate;
    private Double bmr;
}
