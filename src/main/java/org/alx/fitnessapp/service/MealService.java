package org.alx.fitnessapp.service;

import org.alx.fitnessapp.exception.MealCreationExceptionAbstract;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.MealDTO;

public interface MealService {

    String createOrUpdateMeal(MealDTO dto) throws MealCreationExceptionAbstract;

    String deleteMealPlan(MealDTO dto);

    String removeFoodFromMeal(MealDTO mealDTO);

    MealDTO getMealByDay(DayDTO dayDTO, String mealName);
}
