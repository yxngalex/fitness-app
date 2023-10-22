package org.alx.fitnessapp.model.dto;

import lombok.Data;
import org.alx.fitnessapp.model.entity.Category;
import org.alx.fitnessapp.model.entity.Exercise;
import org.alx.fitnessapp.model.entity.Goal;

import java.time.LocalDate;
import java.util.List;

@Data
public class WorkoutRoutineDTO {

    private List<ExerciseStatDTO> exercisesStats;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private Category category;
    private Goal goal;

}
