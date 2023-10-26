package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.GoalDTO;


public interface GoalService {
    String createOrUpdateGoal(GoalDTO goalDTO);

    GoalDTO getGoal();

    String deleteGoal(Integer goalId);

}
