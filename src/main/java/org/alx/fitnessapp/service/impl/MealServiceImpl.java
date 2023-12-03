package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.FoodDTOConverter;
import org.alx.fitnessapp.converter.MealDTOConverter;
import org.alx.fitnessapp.exception.DayException;
import org.alx.fitnessapp.exception.MealCreationException;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.FoodDTO;
import org.alx.fitnessapp.model.dto.MealDTO;
import org.alx.fitnessapp.model.dto.OverviewFoodEntriesDTO;
import org.alx.fitnessapp.model.entity.*;
import org.alx.fitnessapp.repository.DayRepository;
import org.alx.fitnessapp.repository.FoodRepository;
import org.alx.fitnessapp.repository.MealRepository;
import org.alx.fitnessapp.repository.NutritionRepository;
import org.alx.fitnessapp.service.MealService;
import org.alx.fitnessapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final UserService userService;
    private final DayRepository dayRepository;
    private final FoodRepository foodRepository;
    private final NutritionRepository nutritionRepository;
    private final MealDTOConverter mealDTOConverter;
    private final FoodDTOConverter foodDTOConverter;

    @Override
    public String createOrUpdateMeal(MealDTO dto) throws MealCreationException {
        User loggedUser = userService.getLoggedUser();
        Day day = dayRepository.findDayByUserIdAndLoggedDate(loggedUser.getId(), dto.getDayDTO().getLoggedDate());
        Meal existingMeal = mealRepository.findMealByMealNameForDay(dto.getMealName(), day, loggedUser.getUsername());

        if (existingMeal == null) {
            List<Food> foods = new ArrayList<>();

            if (!dto.getFoodList().isEmpty()) {
                for (FoodDTO foodDTO : dto.getFoodList()) {
                    for (int i = 0; i < foodDTO.getServing(); i++) {
                        Food f = foodRepository.findFoodByFoodName(foodDTO.getFoodName());
                        foods.add(f);
                    }
                }
            } else {
                throw new MealCreationException("Food list can't be empty!");
            }

            Meal meal = new Meal();
            if (day != null) {
                Nutrition nutritionPerMeal = countNutritionPerMeal(dto, day, loggedUser.getUsername());

                meal.setDay(day);
                meal.setMealName(dto.getMealName());
                meal.setFoodList(foods);
                meal.setNutrition(nutritionPerMeal);

                mealRepository.save(meal);

                if (day.getNutrition() != null) {
                    day.setNutrition(nutritionRepository.save(countNutritionPerDay(day, dto)));
                } else {
                    Nutrition nutritionForDay = new Nutrition();
                    nutritionForDay.setProtein(nutritionPerMeal.getProtein());
                    nutritionForDay.setFat(nutritionPerMeal.getFat());
                    nutritionForDay.setCarbs(nutritionPerMeal.getCarbs());
                    nutritionForDay.setCalories(nutritionPerMeal.getCalories());

                    day.setNutrition(nutritionRepository.save(nutritionForDay));
                }

                dayRepository.save(day);

                return meal.getMealName() + " saved!";
            } else {
                throw new DayException("Day must exist");
            }
        } else {
            List<Food> existingFoods = foodRepository.findAllByMealListId(existingMeal.getId());
            if (!dto.getFoodList().isEmpty()) {
                for (FoodDTO foodDTO : dto.getFoodList()) {
                    for (int i = 0; i < foodDTO.getServing(); i++) {
                        Food f = foodRepository.findFoodByFoodName(foodDTO.getFoodName());
                        existingFoods.add(f);
                    }
                }
            } else {
                throw new MealCreationException("Food list mustn't be empty!");
            }
            if (day != null) {
                Nutrition nutritionPerMeal = countNutritionPerMeal(dto, day, loggedUser.getUsername());

                existingMeal.setDay(day);
                existingMeal.setMealName(dto.getMealName());
                existingMeal.setFoodList(existingFoods);
                existingMeal.setNutrition(nutritionPerMeal);

                mealRepository.save(existingMeal);

                if (day.getNutrition() != null) {
                    day.setNutrition(nutritionRepository.save(countNutritionPerDay(day, dto)));
                } else {
                    Nutrition nutritionForDay = new Nutrition();
                    nutritionForDay.setProtein(nutritionPerMeal.getProtein());
                    nutritionForDay.setFat(nutritionPerMeal.getFat());
                    nutritionForDay.setCarbs(nutritionPerMeal.getCarbs());
                    nutritionForDay.setCalories(nutritionPerMeal.getCalories());

                    day.setNutrition(nutritionRepository.save(nutritionForDay));
                }

                dayRepository.save(day);

                return existingMeal.getMealName() + " updated!";
            } else {
                throw new DayException("Day must exist");
            }
        }
    }

    @Override
    public String deleteMealPlan(MealDTO dto) {
        User loggedUser = userService.getLoggedUser();
        Meal meal = mealRepository.findMealByMealName(dto.getMealName(), loggedUser.getUsername());
        String mealName = meal.getMealName();
        Nutrition dayNutrition = nutritionRepository.findNutritionForDay(meal.getDay());

        dayNutrition.setProtein(dayNutrition.getProtein() - meal.getNutrition().getProtein());
        dayNutrition.setCarbs(dayNutrition.getCarbs() - meal.getNutrition().getCarbs());
        dayNutrition.setCalories(dayNutrition.getCalories() - meal.getNutrition().getCalories());
        dayNutrition.setFat(dayNutrition.getFat() - meal.getNutrition().getFat());

        nutritionRepository.save(dayNutrition);

        mealRepository.delete(meal);
        nutritionRepository.delete(meal.getNutrition());

        return mealName + " deleted";
    }

    @Override
    public String removeFoodFromMeal(MealDTO mealDTO) {
        User loggedUser = userService.getLoggedUser();
        Meal findExistingMeal = mealRepository.findMealByMealName(mealDTO.getMealName(), loggedUser.getUsername());

        List<Food> foodsInMeal = findExistingMeal.getFoodList();

        Food food = foodRepository.findFoodByFoodName(mealDTO.getFoodList().get(0).getFoodName());
        Nutrition foodNutrition = food.getNutrition();
        Nutrition mealNutrition = findExistingMeal.getNutrition();
        Nutrition dayNutrition = nutritionRepository.findNutritionForDay(findExistingMeal.getDay());

        mealNutrition.setProtein(mealNutrition.getProtein() - foodNutrition.getProtein());
        mealNutrition.setCarbs(mealNutrition.getCarbs() - foodNutrition.getCarbs());
        mealNutrition.setCalories(mealNutrition.getCalories() - foodNutrition.getCalories());
        mealNutrition.setFat(mealNutrition.getFat() - foodNutrition.getFat());

        nutritionRepository.save(mealNutrition);

        dayNutrition.setProtein(dayNutrition.getProtein() - foodNutrition.getProtein());
        dayNutrition.setCarbs(dayNutrition.getCarbs() - foodNutrition.getCarbs());
        dayNutrition.setCalories(dayNutrition.getCalories() - foodNutrition.getCalories());
        dayNutrition.setFat(dayNutrition.getFat() - foodNutrition.getFat());

        nutritionRepository.save(dayNutrition);

        foodsInMeal.remove(food);

        findExistingMeal.setFoodList(foodsInMeal);

        mealRepository.save(findExistingMeal);


        return food.getFoodName() + " removed from " + findExistingMeal.getMealName();
    }

    @Override
    public MealDTO getMealByDay(DayDTO dayDTO, String mealName) {
        User loggedUser = userService.getLoggedUser();

        Day day = dayRepository.findDayByUserIdAndLoggedDate(loggedUser.getId(), dayDTO.getLoggedDate());

        Meal findExistingMeal = mealRepository.findMealByDay(day, mealName);

        return mealDTOConverter.convertMealToMealDTO(findExistingMeal);
    }

    @Override
    public List<OverviewFoodEntriesDTO> getMealFoodEntries() {
        User loggedUser = userService.getLoggedUser();

        List<Meal> allMeals = mealRepository.findAllMealsByUser(loggedUser.getId());
        List<OverviewFoodEntriesDTO> mealFoodEntries = new ArrayList<>();

        for (Meal meal : allMeals) {
            String mealName = meal.getMealName();
            List<Food> foodList = meal.getFoodList();

            List<FoodDTO> foodDTOList = new ArrayList<>();

            for (Food food : foodList) {
                FoodDTO foodDTO = foodDTOConverter.convertFoodToFoodDTO(food);
                foodDTOList.add(foodDTO);
            }

            OverviewFoodEntriesDTO mealFoodEntryDTO = new OverviewFoodEntriesDTO();
            mealFoodEntryDTO.setMealName(mealName);
            mealFoodEntryDTO.setFoods(foodDTOList);

            mealFoodEntries.add(mealFoodEntryDTO);
        }

        return mealFoodEntries;
    }

    private Nutrition countNutritionPerMeal(MealDTO dto, Day d, String username) {
        Nutrition existingNutrition = nutritionRepository.findNutritionForMealForDay(dto.getMealName(), d, username);

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

    private Nutrition countNutritionPerDay(Day day, MealDTO dto) {
        Nutrition dayNutrition = day.getNutrition();

        for (FoodDTO foodDTO : dto.getFoodList()) {
            double calories = 0.0;
            double protein = 0.0;
            double carbs = 0.0;
            double fat = 0.0;

            for (int i = 0; i < foodDTO.getServing(); i++) {
                calories += foodDTO.getNutritionDTO().getCalories();
                protein += foodDTO.getNutritionDTO().getProtein();
                fat += foodDTO.getNutritionDTO().getFat();
                carbs += foodDTO.getNutritionDTO().getCarbs();
            }
            dayNutrition.setCalories(dayNutrition.getCalories() + calories);
            dayNutrition.setProtein(dayNutrition.getProtein() + protein);
            dayNutrition.setFat(dayNutrition.getFat() + fat);
            dayNutrition.setCarbs(dayNutrition.getCarbs() + carbs);
        }

        return dayNutrition;
    }

}
