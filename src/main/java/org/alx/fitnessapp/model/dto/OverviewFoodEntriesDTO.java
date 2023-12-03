package org.alx.fitnessapp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class OverviewFoodEntriesDTO {

    String mealName;
    List<FoodDTO> foods;

}
