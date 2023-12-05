package org.alx.fitnessapp.model.dto;

import lombok.Data;


@Data
public class FoodDTO {
    private NutritionDTO nutritionDTO;
    private String foodName;
    private String foodGroup;
    private Integer serving;
}
