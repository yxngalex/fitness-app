package org.alx.fitnessapp.service;


import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;

public interface WorkoutRoutineService {

    String createWorkoutRoutineWithGoal();

    String createWorkoutRoutineWithoutGoal(WorkoutRoutineDTO workoutRoutineDTO);
}
