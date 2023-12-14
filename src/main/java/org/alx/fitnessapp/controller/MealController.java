package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.exception.DayException;
import org.alx.fitnessapp.exception.MealCreationException;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.MealDTO;
import org.alx.fitnessapp.service.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping("/create")
    public ResponseEntity<String> createMeal(@RequestBody MealDTO mealDTO) {
        try {
            return ResponseEntity.ok(mealService.createOrUpdateMeal(mealDTO));
        } catch (MealCreationException | DayException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFoodFromMeal(@RequestBody MealDTO mealDTO) {
        return ResponseEntity.ok(mealService.removeFoodFromMeal(mealDTO));
    }

    @PostMapping("/getAllMealsInADay")
    public ResponseEntity<List<MealDTO>> getMealsInADay(@RequestBody DayDTO dayDTO) {
        return ResponseEntity.ok(mealService.getMealsInADay(dayDTO));
    }

    @GetMapping("/get/{mealName}")
    public ResponseEntity<MealDTO> getMealByDay(@RequestBody DayDTO dayDTO, @PathVariable("mealName") String mealName) {
        return ResponseEntity.ok(mealService.getMealByDay(dayDTO, mealName));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMealPlan(@RequestBody MealDTO mealDTO) {
        return ResponseEntity.ok(mealService.deleteMealPlan(mealDTO));
    }
}
