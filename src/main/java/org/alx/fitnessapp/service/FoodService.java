package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.FoodDTO;

import java.util.List;

public interface FoodService {

    List<FoodDTO> getAllFoods();

    List<FoodDTO> getFoodAutoComplete(String foodName);

}
