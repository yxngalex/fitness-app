package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.CategoryDTO;
import org.alx.fitnessapp.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<CategoryDTO> getAllCategories() {
        return null;
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return null;
    }
}
