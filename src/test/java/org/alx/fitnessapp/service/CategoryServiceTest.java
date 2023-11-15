package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.model.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoryServiceTest extends ConfigBaseTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void getAllRandomizedTest() {
        List<Category> categoriesRandomized = categoryService.getAllCategoriesRandomized();
        int categoriesWithoutMixed = 6;

        assertFalse(categoriesRandomized.isEmpty());
        assertEquals(categoriesWithoutMixed, categoriesRandomized.size());
    }

    @Test
    void getMixedCategory() {
        Category mixedCategory = categoryService.getMixedCategory();

        assertNotNull(mixedCategory);
        assertEquals("MIXED", mixedCategory.getCategoryName());
    }
}
