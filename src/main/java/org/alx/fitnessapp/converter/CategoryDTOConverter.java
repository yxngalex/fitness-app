package org.alx.fitnessapp.converter;

import org.alx.fitnessapp.model.dto.CategoryDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.Category;
import org.alx.fitnessapp.model.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDTOConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public CategoryDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        this.modelMapper = modelMapper;
    }

    public CategoryDTO convertCategoryToCategoryDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category convertCategoryDTOToCategory(CategoryDTO dto) {
        return modelMapper.map(dto, Category.class);
    }
}
