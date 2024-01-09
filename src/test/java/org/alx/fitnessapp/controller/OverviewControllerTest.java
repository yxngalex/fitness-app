package org.alx.fitnessapp.controller;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.service.DayService;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alx.fitnessapp.model.dto.*;
import org.alx.fitnessapp.service.MealService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;


@SpringBootTest
public class OverviewControllerTest extends ConfigBaseTest {

    @MockBean
    private DayService dayService;

    @MockBean
    private MealService mealService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getOverallNutritionTest() throws Exception {
        NutritionDTO nutritionDTO = new NutritionDTO();
        nutritionDTO.setCarbs(1d);
        nutritionDTO.setProtein(1d);
        nutritionDTO.setFat(1d);
        nutritionDTO.setCalories(1d);

        when(dayService.getOverallNutrition()).thenReturn(nutritionDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/overview/getNutrition")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(nutritionDTO)));
    }

    @Test
    public void getMealFoodEntriesTest() throws Exception {
        NutritionDTO foodNutrition = new NutritionDTO();
        foodNutrition.setCarbs(1d);
        foodNutrition.setProtein(1d);
        foodNutrition.setFat(1d);
        foodNutrition.setCalories(1d);


        FoodDTO f = new FoodDTO();
        f.setNutritionDTO(foodNutrition);
        f.setServing(1);
        f.setFoodName("test");
        f.setFoodGroup("test");

        List<FoodDTO> foodDTOList = Collections.singletonList(f);

        OverviewFoodEntriesDTO foodEntriesDTO = new OverviewFoodEntriesDTO();
        foodEntriesDTO.setFoods(foodDTOList);
        foodEntriesDTO.setMealName("test");

        when(mealService.getMealFoodEntries()).thenReturn(Collections.singletonList(foodEntriesDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/overview/getFoodEntries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
    }

    @Test
    public void calculateBmiTest() throws Exception {
        BmiDTO bmiDTO = new BmiDTO();
        bmiDTO.setBmiCategory("test");
        bmiDTO.setBmi(123d);

        when(dayService.calculateBmi()).thenReturn(bmiDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/overview/calculateBmi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(bmiDTO)));
    }

}
