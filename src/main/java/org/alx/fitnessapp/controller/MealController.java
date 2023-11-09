package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.exception.DayExceptionAbstract;
import org.alx.fitnessapp.exception.MealCreationExceptionAbstract;
import org.alx.fitnessapp.model.dto.DayDTO;
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
        } catch (MealCreationExceptionAbstract | DayExceptionAbstract e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeFoodFromMeal(@RequestBody MealDTO mealDTO) {
        return ResponseEntity.ok(mealService.removeFoodFromMeal(mealDTO));
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
