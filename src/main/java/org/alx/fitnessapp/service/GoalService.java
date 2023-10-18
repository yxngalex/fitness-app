package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.GoalDTO;

import java.util.List;


public interface GoalService {
    String createGoal(GoalDTO goalDTO);

    GoalDTO getGoal();

    GoalDTO updateGoal(GoalDTO goalDTO);

    String deleteGoal(Integer goalId);

}
