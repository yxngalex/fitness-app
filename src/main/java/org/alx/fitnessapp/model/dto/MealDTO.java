package org.alx.fitnessapp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class MealDTO {
    private DayDTO dayDTO;
    private List<FoodDTO> foodList;
    private NutritionDTO nutrition;
    private String mealName;
}
