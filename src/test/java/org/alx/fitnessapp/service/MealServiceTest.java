package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.converter.DayDTOConverter;
import org.alx.fitnessapp.converter.FoodDTOConverter;
import org.alx.fitnessapp.converter.MealDTOConverter;
import org.alx.fitnessapp.exception.MealCreationException;
import org.alx.fitnessapp.model.dto.*;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MealServiceTest extends ConfigBaseTest {

    @Autowired
    private MealService mealService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private DayDTOConverter dayDTOConverter;
    @Autowired
    private FoodDTOConverter foodDTOConverter;
    @Autowired
    private MealDTOConverter mealDTOConverter;
    @MockBean
    private FoodRepository foodRepository;

    DayDTO dayDTO;
    List<FoodDTO> foodDTOList;

    @BeforeEach
    void setupMeal() {
        NutritionDTO dayNutritionDTO = new NutritionDTO();
        dayNutritionDTO.setProtein(2.0);
        dayNutritionDTO.setFat(2.0);
        dayNutritionDTO.setCalories(2.0);
        dayNutritionDTO.setCarbs(2.0);

        dayDTO = new DayDTO();
        dayDTO.setBmr(2500.4);
        dayDTO.setLoggedDate(LocalDate.now());
        dayDTO.setNutritionDTO(dayNutritionDTO);

        NutritionDTO n1 = new NutritionDTO();
        n1.setProtein(2.0);
        n1.setFat(2.0);
        n1.setCalories(2.0);
        n1.setCarbs(2.0);

        NutritionDTO n2 = new NutritionDTO();
        n2.setProtein(2.0);
        n2.setFat(2.0);
        n2.setCalories(2.0);
        n2.setCarbs(2.0);

        NutritionDTO n3 = new NutritionDTO();
        n3.setProtein(2.0);
        n3.setFat(2.0);
        n3.setCalories(2.0);
        n3.setCarbs(2.0);

        FoodDTO f1 = new FoodDTO();
        f1.setFoodName("Milk");
        f1.setServing(1);
        f1.setNutritionDTO(n1);

        FoodDTO f2 = new FoodDTO();
        f2.setFoodName("Apple");
        f2.setServing(1);
        f2.setNutritionDTO(n2);

        FoodDTO f3 = new FoodDTO();
        f3.setFoodName("Tuna");
        f3.setServing(1);
        f3.setNutritionDTO(n3);

        foodDTOList = new ArrayList<>();

        foodDTOList.add(f1);
        foodDTOList.add(f2);
        foodDTOList.add(f3);


        when(nutritionRepository.findNutritionForDay(any(Day.class))).thenReturn(dayDTOConverter.convertDayDTOToDay(dayDTO).getNutrition());
    }

    @Test
    void createOrUpdateMeal_create() throws MealCreationException {
        MealDTO mealDTO = new MealDTO();
        mealDTO.setMealName("Breakfast test");
        mealDTO.setDayDTO(dayDTO);
        mealDTO.setFoodList(foodDTOList);

        String result = mealService.createOrUpdateMeal(mealDTO);
        String expected = mealDTO.getMealName() + " saved!";

        assertEquals(expected, result);
        verify(mealRepository, times(1)).save(any(Meal.class));
        verify(dayRepository, times(1)).save(any(Day.class));
        // Saves one for day, and one for meal
        verify(nutritionRepository, times(2)).save(any(Nutrition.class));
    }

    @Test
    void createOrUpdateMeal_update() throws MealCreationException {
        MealDTO mealDTO = new MealDTO();
        mealDTO.setMealName("Lunch");
        mealDTO.setDayDTO(dayDTO);
        mealDTO.setFoodList(foodDTOList);

        when(mealRepository.findMealByMealNameForDay(anyString(), any(Day.class), anyString())).thenReturn(mealDTOConverter.convertMealDTOToMeal(mealDTO));

        String result = mealService.createOrUpdateMeal(mealDTO);
        String expected = mealDTO.getMealName() + " updated!";

        assertEquals(expected, result);
        verify(mealRepository, times(1)).save(any(Meal.class));
        verify(dayRepository, times(1)).save(any(Day.class));
        // Saves one for day, and one for meal
        verify(nutritionRepository, times(2)).save(any(Nutrition.class));
    }

    @Test
    void deleteMealPlan() {
        MealDTO mealDTO = new MealDTO();
        NutritionDTO nutritionDTO = new NutritionDTO();
        nutritionDTO.setProtein(1.0);
        nutritionDTO.setFat(1.0);
        nutritionDTO.setCalories(1.0);
        nutritionDTO.setCarbs(1.0);

        mealDTO.setMealName("Breakfast test");
        mealDTO.setDayDTO(dayDTO);
        mealDTO.setFoodList(foodDTOList);
        mealDTO.setNutrition(nutritionDTO);

        User user = userService.getLoggedUser();

        when(mealRepository.findMealByMealName(mealDTO.getMealName(), user.getUsername()))
                .thenReturn(mealDTOConverter.convertMealDTOToMeal(mealDTO));

        ArgumentCaptor<Meal> mealCaptor = ArgumentCaptor.forClass(Meal.class);

        mealService.deleteMealPlan(mealDTO);

        verify(mealRepository).findMealByMealName(eq(mealDTO.getMealName()), eq(user.getUsername()));

        verify(mealRepository).delete(mealCaptor.capture());

        Meal capturedMeal = mealCaptor.getValue();
        assertNotNull(capturedMeal);
        assertEquals(mealDTO.getMealName(), capturedMeal.getMealName());
    }


    @Test
    void removeFoodFromMeal() {
        Meal existingMeal = new Meal();
        existingMeal.setId(1);
        existingMeal.setMealName("Lunch");

        Food foodToRemove = new Food();
        foodToRemove.setFoodName("Chicken");
        Nutrition n = new Nutrition();
        n.setProtein(2.0);
        n.setFat(2.0);
        n.setCalories(2.0);
        n.setCarbs(2.0);

        foodToRemove.setNutrition(n);

        List<Food> foodsInMeal = new ArrayList<>();
        foodsInMeal.add(foodToRemove);

        existingMeal.setFoodList(foodsInMeal);
        existingMeal.setNutrition(n);
        existingMeal.setDay(dayDTOConverter.convertDayDTOToDay(dayDTO));

        when(mealRepository.findMealByMealName(anyString(), anyString())).thenReturn(existingMeal);

        when(foodRepository.findFoodByFoodName(eq("Chicken"))).thenReturn(foodToRemove);

        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setFoodName("Chicken");

        MealDTO mealDTO = new MealDTO();
        mealDTO.setMealName("Lunch");
        mealDTO.setFoodList(List.of(foodDTO));

        String result = mealService.removeFoodFromMeal(mealDTO);

        assertEquals("Chicken removed from Lunch", result);

        verify(mealRepository).save(existingMeal);

        verify(nutritionRepository, times(2)).save(any(Nutrition.class));
    }


    @Test
    void getMealByDay() {
        User user = userService.getLoggedUser();

        DayDTO dayDTO = new DayDTO();
        dayDTO.setLoggedDate(LocalDate.now());

        NutritionDTO nutritionDTO = new NutritionDTO();
        nutritionDTO.setProtein(1.0);
        nutritionDTO.setFat(1.0);
        nutritionDTO.setCalories(1.0);
        nutritionDTO.setCarbs(1.0);

        MealDTO mealDTO = new MealDTO();
        mealDTO.setMealName("Lunch");
        mealDTO.setDayDTO(dayDTO);
        mealDTO.setFoodList(foodDTOList);
        mealDTO.setNutrition(nutritionDTO);

        Meal existingMeal = mealDTOConverter.convertMealDTOToMeal(mealDTO);

        Day existingDay = new Day();
        existingDay.setId(1);
        existingDay.setUser(user);
        existingDay.setBmr(123.4);

        Nutrition n = new Nutrition();
        n.setId(1);
        n.setProtein(2.0);
        n.setFat(2.0);
        n.setCarbs(2.0);
        n.setCalories(2.0);
        existingDay.setNutrition(n);
        existingDay.setLoggedDate(dayDTO.getLoggedDate());

        when(mealRepository.findMealByDay(any(), anyString())).thenReturn(existingMeal);

        MealDTO result = mealService.getMealByDay(dayDTO, mealDTO.getMealName());

        MealDTO expected = mealDTOConverter.convertMealToMealDTO(existingMeal);

        assertEquals(expected, result);
    }

}
