package org.alx.fitnessapp.service;

import org.alx.fitnessapp.model.dto.CategoryDTO;
import org.alx.fitnessapp.model.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategoriesRandomized();

    Category getMixedCategory();

    List<CategoryDTO> getAllCategories();

}
