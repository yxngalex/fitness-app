package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.MealDTOConverter;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.FoodDTO;
import org.alx.fitnessapp.model.dto.MealDTO;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.DayRepository;
import org.alx.fitnessapp.repository.FoodRepository;
import org.alx.fitnessapp.repository.MealRepository;
import org.alx.fitnessapp.repository.NutritionRepository;
import org.alx.fitnessapp.service.DayService;
import org.alx.fitnessapp.service.MealService;
import org.alx.fitnessapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final MealDTOConverter converter;
    private final UserService userService;
    private final DayService dayService;
    private final DayRepository dayRepository;
    private final FoodRepository foodRepository;
    private final NutritionRepository nutritionRepository;

    // Create meal with foods also count nutrition per meal and finally set nutrition in day with all meals
    @Override
    public String createOrUpdateMeal(MealDTO dto) throws Exception {
        User loggedUser = userService.getLoggedUser();
        Meal existingMeal = mealRepository.findMealByMealName(dto.getMealName(), loggedUser.getUsername());
        Day day = dayRepository.findDayByUserIdAndLoggedDate(loggedUser.getId(), dto.getDayDTO().getLoggedDate());

        if (existingMeal == null) {
            List<Food> foods = new ArrayList<>();

            if (!dto.getFoodList().isEmpty()) {
                for (FoodDTO foodDTO : dto.getFoodList()) {
                    Food f = foodRepository.findFoodByFoodName(foodDTO.getFoodName());
                    foods.add(f);
                }
            } else {
                throw new Exception("Food list can't be empty!");
            }

            Meal meal = new Meal();
            if (day != null) {
                meal.setDay(day);
                meal.setMealName(dto.getMealName());
                meal.setFoodList(foods);
                meal.setNutrition(countNutritionPerMeal(dto));

                mealRepository.save(meal);
            } else {
                throw new Exception("Day must exist");
            }
            return meal.getMealName() + " saved!";
        } else {
            List<Food> existingFoods = foodRepository.findAllByMealListId(existingMeal.getId());
            if (!dto.getFoodList().isEmpty()) {
                for (FoodDTO foodDTO : dto.getFoodList()) {
                    Food f = foodRepository.findFoodByFoodName(foodDTO.getFoodName());
                    existingFoods.add(f);
                }
            } else {
                throw new Exception("Food list mustn't be empty!");
            }
            if (day != null) {
                existingMeal.setDay(day);
                existingMeal.setMealName(dto.getMealName());
                existingMeal.setFoodList(existingFoods);
                existingMeal.setNutrition(countNutritionPerMeal(dto));

                mealRepository.save(existingMeal);

                return existingMeal.getMealName() + " updated!";
            } else {
                throw new Exception("Day must exist");
            }
        }
    }

    private Nutrition countNutritionPerMeal(MealDTO dto) {
        Nutrition existingNutrition = nutritionRepository.findNutritionForMeal(dto.getMealName());

        if (existingNutrition != null) {
            double calories = existingNutrition.getCalories();
            double protein = existingNutrition.getProtein();
            double carbs = existingNutrition.getCarbs();
            double fat = existingNutrition.getFat();

            for (FoodDTO food : dto.getFoodList()) {
                calories += food.getNutritionDTO().getCalories() * food.getServing();
                protein += food.getNutritionDTO().getProtein() * food.getServing();
                carbs += food.getNutritionDTO().getCarbs() * food.getServing();
                fat += food.getNutritionDTO().getFat() * food.getServing();
            }

            existingNutrition.setCalories(calories);
            existingNutrition.setProtein(protein);
            existingNutrition.setCarbs(carbs);
            existingNutrition.setFat(fat);
            return nutritionRepository.save(existingNutrition);
        } else {
            Nutrition newNutrition = new Nutrition();
            double calories = 0.0;
            double protein = 0.0;
            double carbs = 0.0;
            double fat = 0.0;

            for (FoodDTO food : dto.getFoodList()) {
                calories += food.getNutritionDTO().getCalories() * food.getServing();
                protein += food.getNutritionDTO().getProtein() * food.getServing();
                carbs += food.getNutritionDTO().getCarbs() * food.getServing();
                fat += food.getNutritionDTO().getFat() * food.getServing();
            }

            newNutrition.setCalories(calories);
            newNutrition.setProtein(protein);
            newNutrition.setCarbs(carbs);
            newNutrition.setFat(fat);
            return nutritionRepository.save(newNutrition);
        }
    }

}
