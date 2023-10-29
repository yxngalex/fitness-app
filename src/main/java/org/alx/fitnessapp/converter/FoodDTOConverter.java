package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.FoodDTO;
import org.alx.fitnessapp.model.entity.Food;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodDTOConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public FoodDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
    }

    public FoodDTO convertFoodToFoodDTO(Food food) {
        return modelMapper.map(food, FoodDTO.class);
    }

    public Food convertFoodDTOToFood(FoodDTO dto) {
        return modelMapper.map(dto, Food.class);
    }
}
