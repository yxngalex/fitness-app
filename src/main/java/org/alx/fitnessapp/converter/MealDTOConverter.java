package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.MealDTO;
import org.alx.fitnessapp.model.entity.Meal;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MealDTOConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public MealDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
    }

    public MealDTO convertMealToMealDTO(Meal meal) {
        return modelMapper.map(meal, MealDTO.class);
    }

    public Meal convertMealDTOToMeal(MealDTO dto) {
        return modelMapper.map(dto, Meal.class);
    }
}
