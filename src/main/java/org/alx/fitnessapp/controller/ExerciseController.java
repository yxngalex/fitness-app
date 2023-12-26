package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ExerciseDTO>> getAllExercise() {
        return ResponseEntity.ok(exerciseService.getAllExercise());
    }

    @GetMapping("/getAutoComplete/{value}")
    public ResponseEntity<List<ExerciseDTO>> getAllExercise(@PathVariable("value") String value) {
        return ResponseEntity.ok(exerciseService.getExerciseAutoComplete(value));
    }

    @GetMapping("/getAllByCategoryName")
    public ResponseEntity<List<ExerciseDTO>> getAllExerciseByCategoryName(@RequestParam String categoryName) {
        return ResponseEntity.ok(exerciseService.getAllExerciseByCategoryName(categoryName));
    }
}
