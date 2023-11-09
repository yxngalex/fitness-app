package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.DayDTO;
import org.alx.fitnessapp.model.dto.FoodDTO;
import org.alx.fitnessapp.model.dto.MealDTO;
import org.alx.fitnessapp.service.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping("/create")
    public ResponseEntity<String> createMeal(@RequestBody MealDTO mealDTO) {
        try {
            return ResponseEntity.ok(mealService.createOrUpdateMeal(mealDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFoodFromMeal(@RequestBody MealDTO mealDTO) {
        return ResponseEntity.ok(mealService.removeFoodFromMeal(mealDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<MealDTO> getMealByDay(@RequestBody DayDTO dayDTO) {
        return ResponseEntity.ok(mealService.getMealByDay(dayDTO));
    }
}
