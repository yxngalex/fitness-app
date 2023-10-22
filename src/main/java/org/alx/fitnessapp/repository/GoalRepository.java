package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {

    Goal findByWeightGoalAndBodyTypeGoalAndWeeklyExercise(Double weightGoal, String bodyTypeGoal, Integer weeklyExercise);

    Boolean existsGoalByWeightGoalAndBodyTypeGoalAndWeeklyExercise(Double weightGoal, String bodyTypeGoal, Integer weeklyExercise);
}
