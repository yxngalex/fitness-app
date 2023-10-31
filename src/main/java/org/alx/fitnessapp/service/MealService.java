package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.FoodDTO;
import org.alx.fitnessapp.model.dto.MealDTO;

import java.util.List;

public interface MealService {

    String createMeal(MealDTO dto) throws Exception;
}
