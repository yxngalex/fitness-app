package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.entity.Exercise;

import java.util.List;

public interface ExerciseService {

    List<Exercise> getRandomExercisesByCategory(String categoryName, int limit);
}
