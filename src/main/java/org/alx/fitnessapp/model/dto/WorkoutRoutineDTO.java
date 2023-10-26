package org.alx.fitnessapp.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class WorkoutRoutineDTO {

    private List<ExerciseStatsDTO> exerciseStatsDTO;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private CategoryDTO categoryDTO;
    private GoalDTO goalDTO;

}
