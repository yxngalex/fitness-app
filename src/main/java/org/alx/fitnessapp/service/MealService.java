package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.FoodDTO;

import java.util.List;

public interface MealService {

    String createMeal(List<FoodDTO> food);
}
