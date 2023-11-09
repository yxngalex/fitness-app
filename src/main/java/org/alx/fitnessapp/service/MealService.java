package org.alx.fitnessapp.service;

import org.alx.fitnessapp.exception.MealCreationException;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.MealDTO;

public interface MealService {

    String createOrUpdateMeal(MealDTO dto) throws MealCreationException;

    String deleteMealPlan(MealDTO dto);

    String removeFoodFromMeal(MealDTO mealDTO);

    MealDTO getMealByDay(DayDTO dayDTO, String mealName);
}
