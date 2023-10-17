package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    Goal findByWeightGoalAndBodyTypeGoalAndWeeklyExercise(Double weightGoal, String bodyTypeGoal, Integer weeklyExercise);

    Boolean existsGoalByWeightGoalAndBodyTypeGoalAndWeeklyExercise(Double weightGoal, String bodyTypeGoal, Integer weeklyExercise);
}
