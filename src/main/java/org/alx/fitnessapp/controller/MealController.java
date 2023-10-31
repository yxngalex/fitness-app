package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.MealDTO;
import org.alx.fitnessapp.service.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meal")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping("/create")
    public ResponseEntity<String> createMeal(@RequestBody MealDTO mealDTO) {
        try {
            return ResponseEntity.ok(mealService.createMeal(mealDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
