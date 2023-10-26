package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.CategoryDTO;
import org.alx.fitnessapp.model.entity.Category;
import org.alx.fitnessapp.repository.CategoryRepository;
import org.alx.fitnessapp.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final EntityManager entityManager;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategoriesRandomized() {
        Query q = entityManager.createQuery("SELECT c FROM Category c WHERE c.categoryName NOT LIKE 'MIXED' ORDER BY RAND()");

        return (List<Category>) q.getResultList();
    }

    @Override
    public Category getMixedCategory() {
        return categoryRepository.findCategoryByCategoryName("MIXED");
    }
}
