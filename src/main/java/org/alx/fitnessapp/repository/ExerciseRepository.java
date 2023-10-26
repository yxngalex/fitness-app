package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    Exercise findExerciseByCategoryCategoryNameAndExerciseName(String categoryName, String exerciseName);
}
