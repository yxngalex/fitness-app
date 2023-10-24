package org.alx.fitnessapp.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.model.dto.ExerciseDTO;
import org.alx.fitnessapp.model.dto.WorkoutRoutineDTO;
import org.alx.fitnessapp.model.entity.Exercise;
import org.alx.fitnessapp.service.ExerciseService;
import org.alx.fitnessapp.service.WorkoutRoutineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routine")
@RequiredArgsConstructor
public class WorkoutRoutineController {
    private final WorkoutRoutineService workoutRoutineService;
    private final ExerciseService exerciseService;

    @PostMapping("/create/goal")
    public ResponseEntity<String> createWorkoutRoutineWithGoal() {
        return ResponseEntity.ok(workoutRoutineService.createWorkoutRoutineWithGoal());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWorkoutRoutineWithoutGoal(@RequestBody WorkoutRoutineDTO workoutRoutineDTO) {
        return ResponseEntity.ok(workoutRoutineService.createWorkoutRoutineWithoutGoal(workoutRoutineDTO));
    }
}
