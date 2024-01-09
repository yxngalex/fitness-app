package org.alx.fitnessapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.converter.GoalDTOConverter;
import org.alx.fitnessapp.model.dto.*;
import org.alx.fitnessapp.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class MealControllerTest extends ConfigBaseTest {

    @MockBean
    private MealService mealService;

    @Autowired
    GoalDTOConverter goalConverter;

    @Autowired
    ObjectMapper objectMapper;

    MealDTO mealDTO;

    DayDTO dayDTO;

    @BeforeEach
    public void setupMeal() {
        NutritionDTO dayNutrition = new NutritionDTO();
        dayNutrition.setCarbs(1d);
        dayNutrition.setProtein(1d);
        dayNutrition.setFat(1d);
        dayNutrition.setCalories(1d);

        NutritionDTO nutritionDTO = new NutritionDTO();
        nutritionDTO.setCarbs(1d);
        nutritionDTO.setProtein(1d);
        nutritionDTO.setFat(1d);
        nutritionDTO.setCalories(1d);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryDescription("test");
        categoryDTO.setCategoryName("test");

        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setExerciseName("test");
        exerciseDTO.setExerciseDescription("test");
        exerciseDTO.setCategoryDTO(categoryDTO);

        ExerciseStatsDTO exerciseStatsDTO = new ExerciseStatsDTO();
        exerciseStatsDTO.setExerciseDTO(exerciseDTO);
        exerciseStatsDTO.setSet(1);
        exerciseStatsDTO.setReps(1);
        exerciseStatsDTO.setExerciseWeight(1d);

        List<ExerciseStatsDTO> list = Collections.singletonList(exerciseStatsDTO);

        WorkoutRoutineDTO workoutRoutineDTO = new WorkoutRoutineDTO();
        workoutRoutineDTO.setDateStart(LocalDate.now());
        workoutRoutineDTO.setGoalDTO(goalConverter.convertGoalToGoalDTO(userService.getLoggedUser().getGoal()));
        workoutRoutineDTO.setCategoryDTO(categoryDTO);
        workoutRoutineDTO.setExerciseStatsDTO(list);

        dayDTO = new DayDTO();
        dayDTO.setNutritionDTO(dayNutrition);
        dayDTO.setBmr(123d);
        dayDTO.setLoggedDate(LocalDate.now());
        dayDTO.setWorkoutRoutineDTO(workoutRoutineDTO);

        mealDTO = new MealDTO();
        mealDTO.setMealName("test");
        mealDTO.setNutrition(nutritionDTO);
        mealDTO.setDayDTO(dayDTO);
    }

    @Test
    public void createMealTest() throws Exception {
        when(mealService.createOrUpdateMeal(mealDTO)).thenReturn("Success");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/meal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mealDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }

    @Test
    public void removeFoodFromMealTest() throws Exception {
        when(mealService.removeFoodFromMeal(mealDTO)).thenReturn("Success");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/meal/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mealDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }

    @Test
    public void getMealsInADayTest() throws Exception {

        when(mealService.getMealsInADay(dayDTO)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/meal/getAllMealsInADay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dayDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(0));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}