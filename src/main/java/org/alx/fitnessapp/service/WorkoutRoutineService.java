package org.alx.fitnessapp.service;


import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;

import java.util.List;

public interface WorkoutRoutineService {

    String autoCreateWorkoutRoutine();

    String createWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO);

    List<WorkoutRoutineDTO> getWorkoutRoutineList();

    WorkoutRoutineDTO updateWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO);

    String deleteWorkoutRoutine(WorkoutRoutineDTO workoutRoutineDTO);
}
