package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
