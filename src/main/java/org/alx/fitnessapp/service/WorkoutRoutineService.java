package org.alx.fitnessapp.service;


import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;
import org.alx.fitnessapp.model.entity.WorkoutRoutine;

import java.util.List;

public interface WorkoutRoutineService {

    List<WorkoutRoutine> autoCreateWorkoutRoutine() throws Exception;

    WorkoutRoutine createWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO);

    List<WorkoutRoutineDTO> getWorkoutRoutineList();

    WorkoutRoutineDTO updateWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO);

    String deleteWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO);
}
