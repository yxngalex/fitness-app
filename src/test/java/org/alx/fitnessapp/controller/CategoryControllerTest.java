package org.alx.fitnessapp.controller;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.model.dto.CategoryDTO;
import org.alx.fitnessapp.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryControllerTest extends ConfigBaseTest {

    @MockBean
    private CategoryService categoryService;

    @Test
    public void getAllCountriesTest() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("test");
        categoryDTO.setCategoryDescription("test");

        try {
            List<CategoryDTO> mockCategories = Collections.singletonList(categoryDTO);
            when(categoryService.getAllCategories()).thenReturn(mockCategories);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/category/getAll")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(mockCategories.size()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoryName").value("test"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
