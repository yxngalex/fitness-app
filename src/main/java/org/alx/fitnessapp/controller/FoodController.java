package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.FoodDTO;
import org.alx.fitnessapp.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/getAll")
    public ResponseEntity<List<FoodDTO>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @GetMapping("/getAll/autoComplete/{foodName}")
    public ResponseEntity<List<FoodDTO>> getAllFoodsAutoComplete(@PathVariable("foodName") String foodName) {
        return ResponseEntity.ok(foodService.getFoodAutoComplete(foodName));
    }
}
