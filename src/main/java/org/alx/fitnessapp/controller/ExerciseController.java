package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
