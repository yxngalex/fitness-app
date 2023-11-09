package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.MealDTO;

public interface MealService {

    String createOrUpdateMeal(MealDTO dto) throws Exception;

    String deleteMealPlan(MealDTO dto);
}
