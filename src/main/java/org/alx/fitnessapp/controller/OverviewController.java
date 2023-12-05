package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.BmiDTO;
import org.alx.fitnessapp.model.dto.NutritionDTO;
import org.alx.fitnessapp.model.dto.OverviewFoodEntriesDTO;
import org.alx.fitnessapp.service.DayService;
import org.alx.fitnessapp.service.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/overview")
@RequiredArgsConstructor
@CrossOrigin
public class OverviewController {

    private final DayService dayService;
    private final MealService mealService;


    @GetMapping("/getNutrition")
    public ResponseEntity<NutritionDTO> getOverallNutrition() {
        return ResponseEntity.ok(dayService.getOverallNutrition());
    }

    @GetMapping("/getFoodEntries")
    public ResponseEntity<List<OverviewFoodEntriesDTO>> getMealFoodEntries() {
        return ResponseEntity.ok(mealService.getMealFoodEntries());
    }

    @GetMapping("/calculateBmi")
    public ResponseEntity<BmiDTO> calculateBmi() {
        return ResponseEntity.ok(dayService.calculateBmi());
    }
}
