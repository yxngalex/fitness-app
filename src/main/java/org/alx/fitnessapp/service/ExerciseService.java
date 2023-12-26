package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.model.entity.Exercise;

import java.util.List;

public interface ExerciseService {

    List<Exercise> getRandomExercisesByCategory(String categoryName, int limit);
    List<Exercise> getRandomExercisesWithoutCategory();
    List<ExerciseDTO> getAllExercise();
    List<ExerciseDTO> getExerciseAutoComplete(String value);
    List<ExerciseDTO> getAllExerciseByCategoryName(String categoryName);
}
