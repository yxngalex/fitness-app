package org.alx.fitnessapp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {
    private List<NutritionDTO> nutritionDTO;
    private String foodName;
    private String foodGroup;
    private Integer serving;
}
