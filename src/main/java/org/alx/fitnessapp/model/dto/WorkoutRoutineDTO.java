package org.alx.fitnessapp.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class WorkoutRoutineDTO {

    private List<ExerciseStatsDTO> exerciseStats;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private CategoryDTO categoryDTO;
    private GoalDTO goalDTO;

}
