package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.NutritionDTO;
import org.alx.fitnessapp.model.entity.Nutrition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NutritionDTOConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public NutritionDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
    }

    public NutritionDTO convertNutritionToNutritionDTO(Nutrition food) {
        return modelMapper.map(food, NutritionDTO.class);
    }

    public Nutrition convertNutritionDTOToNutrition(NutritionDTO dto) {
        return modelMapper.map(dto, Nutrition.class);
    }
}
